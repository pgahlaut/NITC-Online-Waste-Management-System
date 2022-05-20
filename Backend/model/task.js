const mongoose = require('mongoose');

const taskSchema = new mongoose.Schema({

    
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
    }

},{collection:'tasks'});

const taskModel = mongoose.model('taskSchema',taskSchema);
module.exports = taskModel;