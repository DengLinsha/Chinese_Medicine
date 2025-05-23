module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  productionSourceMap: false,
  devServer: { 
    proxy: {
      '/api': {
        target: 'http://localhost:9999', // 配置好的后端接口地址
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '' // 以'/api'开头的url会进行接口转发
        }
      }
    },
  },
}
