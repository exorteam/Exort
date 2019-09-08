module.exports = {
    assetsDir: 'static',
    pages: {
        index: {
            entry: 'src/main.js',
            template: 'index.html',
            filename: 'index.html'
        }
    },
    outputDir: 'server/public',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/files': process.env.PROXY_TARGET,
            '/upload': process.env.PROXY_TARGET,
            '/auth': process.env.AUTH_PROXY_TARGET
        }
    }
}
