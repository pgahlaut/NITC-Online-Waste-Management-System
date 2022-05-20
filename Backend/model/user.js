const mongoose = require('mongoose');


const userSchema = new mongoose.Schema({
    email: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    name: {
        type: String,
        required:true
    },
    type:{
        type: Number,
        required: false,
        default:1 // 1 is user, 2 is staff, 0 is admin
    }
},{collection: 'users'});

const model = mongoose.model('userSchema',userSchema);
module.exports = model;