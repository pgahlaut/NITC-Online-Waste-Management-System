const mongoose = require('mongoose');

const feedbackSchema = new mongoose.Schema({

    uuid:{
        required:true,
        type:String
    },
    dustbin_id:{
        required:true,
        type:String
    },
    feedback:{
        required:true,
        type:String
    }
},{collection:'feedbacks'});

const feedModel = mongoose.model('feedbackSchema',feedbackSchema);
module.exports = feedModel;