module.exports = {
  root: true,
  env: {
    node: true,
    browser: true,
    es2021: true
  },
  extends: [
    'plugin:vue/vue3-essential',
    'eslint:recommended'
  ],
  plugins: ['security'],
  parserOptions: {
    ecmaVersion: 2021,
    sourceType: 'module'
  },
  rules: {
    // Security rules que detectarão as vulnerabilidades
    'security/detect-object-injection': 'warn',
    'security/detect-non-literal-regexp': 'warn',
    'security/detect-unsafe-regex': 'warn',
    'security/detect-buffer-noassert': 'warn',
    'security/detect-child-process': 'warn',
    'security/detect-disable-mustache-escape': 'warn',
    'security/detect-eval-with-expression': 'warn',
    'security/detect-no-csrf-before-method-override': 'warn',
    'security/detect-non-literal-fs-filename': 'warn',
    'security/detect-non-literal-require': 'warn',
    'security/detect-possible-timing-attacks': 'warn',
    'security/detect-pseudoRandomBytes': 'warn',
    'no-console': 'warn'
  }
}

