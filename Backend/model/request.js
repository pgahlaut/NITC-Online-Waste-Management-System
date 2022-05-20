const mongoose = require('mongoose');

const requestSchema = new mongoose.Schema({

    uuid:{
        required:true,
        type:String
    },
    title:{
        required:true,
        type:String
    },
    location:{
        required:true,
        type:String
    },
    lat:{
        required:false,
        type:Number,
        default:-1
    },
    long:{
        required:false,
        type:Number,
        default:-1
    },
    status:{
        require:false,
        type:String,
        default:"Pending"
    }

},{collection:'requests'});

const reqModel = mongoose.model('requestSchema',requestSchema);
module.exports = reqModel;