const mongooes = require('mongoose');
const Schema = mongooes.Schema;

const Students = new Schema({
    name: {type: String, },
    msv: {type: String},
    diemTb: {type: Number},
    avatar: {type: String}
})
module.exports = mongooes.model("student", Students);