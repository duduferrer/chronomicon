module.exports={
  extends:[
    "@commitlint/config-conventional",
  ],
  formatter: "@commitlint/format",
  rules: {
    'scope-enum': [2, 'always', ['frontend', 'backend']],
    'scope-empty': [2, 'never'],
  },
}