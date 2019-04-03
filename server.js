express = require('express'),
routes = require('./api/routes/Routes.js');

app = express(),
port = process.env.PORT || 8080;

express()
    .set('view engine', 'ejs')
    .use(require('cookie-parser')())
    .use(require('body-parser').urlencoded({ extended: true }))
    .use("/",routes)
    .get('*', (req, res) => res.redirect('/404.html'))
    .listen(port, () => console.log(`Listening on ${ port }`))

console.log('httpsimulator: ' + port);
