const mongoose = require('mongoose');

const alertSchema = new mongoose.Schema({

    uuid:{
        required:true,
        type:String
    },
    dustbin_id:{
        required:true,
        type:String
    },
    alert:{
        required:true,
        type:String
    },
    status:{
        required:false,
        type:String,
        default:"Pending"
    }
},{collection:'alerts'});

const alertModel = mongoose.model('alertSchema',alertSchema);
module.exports = alertModel;