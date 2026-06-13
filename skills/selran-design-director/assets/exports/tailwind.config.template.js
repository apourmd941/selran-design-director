/**
 * {{PROJECT_NAME}} — Tailwind theme extension
 * Generated from design-system.md. Edit the source, not this file.
 * Works with Tailwind v3 and v4 (v4: copy the `theme.extend` values into your `@theme` block).
 */

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./**/*.{html,js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        'bg-primary': '{{color.bg_primary}}',
        'bg-secondary': '{{color.bg_secondary}}',
        'bg-tertiary': '{{color.bg_tertiary}}',
        'fg-primary': '{{color.fg_primary}}',
        'fg-secondary': '{{color.fg_secondary}}',
        'fg-muted': '{{color.fg_muted}}',
        accent: {
          DEFAULT: '{{color.accent}}',
          hover: '{{color.accent_hover}}',
        },
        border: {
          DEFAULT: '{{color.border}}',
          strong: '{{color.border_strong}}',
        },
        success: '{{color.success}}',
        warning: '{{color.warning}}',
        danger: '{{color.danger}}',
      },
      fontFamily: {
        display: ['{{type.display}}', 'sans-serif'],
        sans: ['{{type.body}}', 'sans-serif'],
        mono: ['{{type.mono}}', 'monospace'],
      },
      fontSize: {
        xs: ['{{type.scale.xs}}px', { lineHeight: '{{type.leading.body}}' }],
        sm: ['{{type.scale.sm}}px', { lineHeight: '{{type.leading.body}}' }],
        base: ['{{type.scale.base}}px', { lineHeight: '{{type.leading.body}}' }],
        lg: ['{{type.scale.lg}}px', { lineHeight: '{{type.leading.display}}' }],
        xl: ['{{type.scale.xl}}px', { lineHeight: '{{type.leading.display}}' }],
        xxl: ['{{type.scale.xxl}}px', { lineHeight: '{{type.leading.display}}' }],
        display: ['{{type.scale.display}}px', { lineHeight: '{{type.leading.display}}' }],
      },
      letterSpacing: {
        display: '{{type.tracking.display}}em',
        body: '{{type.tracking.body}}em',
        caps: '{{type.tracking.caps}}em',
      },
      spacing: {
        '1': '{{spacing.base_unit}}px',
        '2': 'calc({{spacing.base_unit}}px * 2)',
        '3': 'calc({{spacing.base_unit}}px * 3)',
        '4': 'calc({{spacing.base_unit}}px * 4)',
        '6': 'calc({{spacing.base_unit}}px * 6)',
        '8': 'calc({{spacing.base_unit}}px * 8)',
        '12': 'calc({{spacing.base_unit}}px * 12)',
        '16': 'calc({{spacing.base_unit}}px * 16)',
      },
      borderRadius: {
        sm: '{{spacing.radius.sm}}px',
        md: '{{spacing.radius.md}}px',
        lg: '{{spacing.radius.lg}}px',
        full: '{{spacing.radius.full}}px',
      },
      transitionDuration: {
        fast: '{{motion.duration_fast}}ms',
        base: '{{motion.duration_base}}ms',
        slow: '{{motion.duration_slow}}ms',
      },
      transitionTimingFunction: {
        out: '{{motion.easing}}',
        dramatic: '{{motion.easing_dramatic}}',
      },
    },
  },
  plugins: [],
};
