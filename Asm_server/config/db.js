const mongooes = require('mongoose');
mongooes.set('strictQuery', true)

const url_local = "mongodb://127.0.0.1:27017/ASM"

const connect = async() =>{
    try {
        await mongooes.connect(url_local,{
            useNewUrlParser: true,
            useUnifiedTopology: true,
        })
        console.log("Connect colection success");
    } catch (error) {
        console.log("error :"+ error);
        console.log('Connect failed');
    }
}
module.exports = {connect}