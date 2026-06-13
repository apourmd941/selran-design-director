#!/usr/bin/env python3
"""Standalone browser picker server — click-returns-to-skill without the Hub.

Serves an already-populated picker page (e.g. .design/picker.html, produced from
picker-standalone-template.html) on loopback, opens the browser, and BLOCKS until
the user clicks an option. The page POSTs the choice back; this prints the chosen
value to stdout and exits. Stdlib only — no dependencies, no Hub.

  python3 picker-serve.py .design/picker.html              # serve, open, wait → prints choice
  python3 picker-serve.py .design/picker.html --timeout 240 --no-open

Exit codes:
  0  a choice was made — the chosen value (the card's data-choice) is on stdout
  2  timed out / browser never reported — caller should fall back to "tell me the number"
  1  error (file missing, port unavailable, etc.)

Security: binds 127.0.0.1 only; the POST path carries a random nonce so a stray
page can't drive it; single-shot — the first valid POST wins and the server exits.
"""
import argparse
import http.server
import json
import os
import secrets
import sys
import threading
import webbrowser

CONFIG_MARKER = "<!--SELRAN_PICKER_CONFIG-->"


def main():
    ap = argparse.ArgumentParser(description="Standalone loopback picker (no Hub).")
    ap.add_argument("page", help="path to the populated picker HTML")
    ap.add_argument("--timeout", type=float, default=300.0, help="seconds to wait for a click (default 300)")
    ap.add_argument("--no-open", action="store_true", help="don't auto-open the browser (print the URL instead)")
    args = ap.parse_args()

    if not os.path.isfile(args.page):
        print(f"picker-serve: no such file: {args.page}", file=sys.stderr)
        sys.exit(1)

    with open(args.page, encoding="utf-8") as f:
        html = f.read()

    nonce = secrets.token_urlsafe(16)
    result = {"choice": None}
    picked = threading.Event()

    class Handler(http.server.BaseHTTPRequestHandler):
        def log_message(self, *a):  # quiet
            pass

        def _page(self):
            # Inject the loopback POST url at the marker so the page's script enters "served" mode.
            inject = (f'<script>window.__SELRAN_POST='
                      f'{json.dumps(f"http://127.0.0.1:{self.server.server_address[1]}/pick/{nonce}")};</script>')
            body = html.replace(CONFIG_MARKER, inject, 1).encode("utf-8")
            self.send_response(200)
            self.send_header("Content-Type", "text/html; charset=utf-8")
            self.send_header("Content-Length", str(len(body)))
            self.end_headers()
            self.wfile.write(body)

        def do_GET(self):
            if self.path == "/" or self.path.startswith("/?"):
                self._page()
            else:
                self.send_error(404)

        def do_POST(self):
            if self.path != f"/pick/{nonce}":
                self.send_error(403)
                return
            length = int(self.headers.get("Content-Length", 0) or 0)
            try:
                data = json.loads(self.rfile.read(length) or b"{}")
            except ValueError:
                data = {}
            result["choice"] = (data.get("choice") or "").strip()
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.end_headers()
            self.wfile.write(b'{"ok":true}')
            picked.set()

    try:
        srv = http.server.ThreadingHTTPServer(("127.0.0.1", 0), Handler)
    except OSError as e:
        print(f"picker-serve: could not bind loopback: {e}", file=sys.stderr)
        sys.exit(1)

    port = srv.server_address[1]
    url = f"http://127.0.0.1:{port}/"
    t = threading.Thread(target=srv.serve_forever, daemon=True)
    t.start()

    if args.no_open:
        print(f"picker ready: {url}", file=sys.stderr)
    else:
        print(f"opening picker: {url}", file=sys.stderr)
        try:
            webbrowser.open(url)
        except Exception:
            print(f"(couldn't auto-open — visit {url})", file=sys.stderr)

    got = picked.wait(timeout=args.timeout)
    srv.shutdown()

    if got and result["choice"]:
        print(result["choice"])   # the answer, on stdout
        sys.exit(0)
    print("picker-serve: no choice within timeout — fall back to asking for the number", file=sys.stderr)
    sys.exit(2)


if __name__ == "__main__":
    main()
