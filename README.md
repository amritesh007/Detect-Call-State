# Detect-Call-State
It is my task for internship at Tekflo.The functionality of the app is as follows:
Generally whenever a outgoing call is made, if the receiver number is switch off, caller will hear "number is switch off", 
or if the receiver is talking with some other person, caller will hear "number is busy", etc.

It detects following call state when the outgoing call is made and toast the following-

Toast "busy" when the operator is busy. 
Toast "switch-off" when the number is switch off. 
Toast "line-busy" when the receiver is talk with someone while dialing. 
Toast "outside coverage" when the receiver is out of coverage. 
Toast "incorrect" when the dialer dials an incorrect number 
Toast "Noti" when the operator sends rest messages like "low balance, offers", etc.


To implement the above functionality Broadcast Receiver is used.

