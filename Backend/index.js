
const express = require('express');
const loginRoute = require('./login/login.js');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const User = require('./model/user');
const registerRoute = require('./login/register');
const postRoute = require('./post/postReq');
const listRoute = require('./post/listReq');
const feedbackRoute = require('./post/postFeedback');
const alertRoute = require('./post/postAlert');
const listAllReqRoute = require('./admin/listAllRequest');
const deleteRoute = require('./admin/deleteRequest');
const taskRoute = require('./admin/addTask');
const removeStaffRoute = require('./admin/removeStaff');
const getStaffRoute = require('./admin/getStaffs');
const listFeedbackRoute = require('./admin/listFeedbacks');
const listTasksRoute = require('./admin/listTasks');
const removeTaskRoute = require('./admin/removeTask');
const listAlertsRoute = require('./admin/getAlerts');

mongoose.connect('mongodb://localhost:27017/login-app-db',{
  useNewUrlParser: true,
  useUnifiedTopology: true
});
const app = express();
const PORT = 80;
const HOST = '0.0.0.0';

app.use(express.json());
app.use(function(req,res,next){
    res.header("Access-Control-Allow-Origin","*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});
app.use('/api/register',registerRoute);
app.use('/api/login',loginRoute);
app.use('/api/postreq',postRoute);
app.use('/api/listreq',listRoute);
app.use('/api/postfeedback',feedbackRoute);
app.use('/api/postalert',alertRoute);
app.use('/api/listallrequests',listAllReqRoute);
app.use('/api/deleterequest',deleteRoute);
app.use('/api/addtask',taskRoute);
app.use('/api/removestaff',removeStaffRoute);
app.use('/api/getstaffs',getStaffRoute);
app.use('/api/listfeedbacks',listFeedbackRoute);
app.use('/api/listtasks',listTasksRoute);
app.use('/api/removetask',removeTaskRoute);
app.use('/api/listalerts',listAlertsRoute);
app.get('/',function(req,res){
    res.send("hello");
});

app.listen(PORT,HOST,()=>console.log())