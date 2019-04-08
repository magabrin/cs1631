from django.shortcuts import render
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.views.decorators.csrf import ensure_csrf_cookie
import socket

# Create your views here.
HOST = '127.0.0.1'
PORT = 53217
delim = "$$$"

@ensure_csrf_cookie
def index(request):
    return render(request, 'frontend/index.html')

def get_vote(request):
    print("get post called")
    if request.method == "POST":
        print("in post")


        voterEmail = request.POST.get('voterEmail', None)
        posterNumber = request.POST.get('posterNumber', None)        
        xmlcommand = "SScope" + delim + "SIS.Scope1" + delim + "MessageType" + delim + "Setting" + delim + "Reciever" + delim + "VotingComponent" + delim + "Sender" + delim + "SISServer" + delim + "Purpose" + delim + "Vote" + delim + "msgID" + delim + "701" + delim + "VoteID" + delim + str(posterNumber) + delim + "Email" + delim + voterEmail + delim
        print(xmlcommand)
        xmlcommand = xmlcommand.encode()

        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:         # s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            print("made the socket")
            s.connect((HOST, PORT))
            print("made the connection")
            s.send(xmlcommand)
            print("sent the xml")
            # data = s.recv(1024)
            # print(data)
        
    return HttpResponseRedirect("/")

def init_tally(request):
    print("init_tally called")
    if request.method == "POST":
        password = request.POST.get('password', "wrong password")
        numPosters = request.POST.get('numPosters', 1)
        posterArr = []
        for i in range(int(numPosters)):
            posterArr.append(str(i+1))
        posterNumberStr = ";".join(posterArr)
        xmlcommand = "SScope" + delim + "SIS.Scope1" + delim + "MessageType" + delim + "Setting" + delim + "Reciever" + delim + "VotingComponent" + delim + "Sender" + delim + "SISServer" + delim + "Purpose" + delim + "Admin" + delim + "Password" + delim + password + delim + "msgID" + delim + "703" + delim + "CandidateList" + delim + posterNumberStr + delim
        print(xmlcommand)
        xmlcommand = xmlcommand.encode()

        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            print("made the socket")
            s.connect((HOST, PORT))
            print("made the connection")
            s.send(xmlcommand)
            print("sent the xml from line 58")
            data = s.recv(1024)
            print(data)
    return HttpResponse("success")


def show_results(request):
    if request.method == "POST":
        n = request.POST.get('n',1)
        password = request.POST.get('password', "wrong password")
        xmlcommand = "SScope" + delim + "SIS.Scope1" + delim + "MessageType" + delim + "Setting" + delim + "Reciever" + delim + "VotingComponent" + delim + "Sender" + delim + "SISServer" + delim + "Purpose" + delim + "Admin" + delim + "Password" + delim + password + delim + "msgID" + delim + "702" + delim + "N" + delim + n + delim
        xmlcommand = xmlcommand.encode()

        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            print("made the socket")
            s.connect((HOST, PORT))
            print("made the connection")
            s.send(xmlcommand)
            print("sent the xml")
            # data = s.recv(1024)
            # print(data)
    return HttpResponse("success")



def kill_voting(request):
    if request.method == "POST":        
        password = request.POST.get('password', "wrong password")
        xmlcommand = "SScope" + delim + "SIS.Scope1" + delim + "MessageType" + delim + "Setting" + delim + "Reciever" + delim + "VotingComponent" + delim + "Sender" + delim + "SISServer" + delim + "Purpose" + delim + "Admin" + delim + "Password" + delim + password + delim + "msgID" + delim + "22" + delim + "Name" + delim + "Kill" + delim
        xmlcommand = xmlcommand.encode()

        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            print("made the socket")
            s.connect((HOST, PORT))
            print("made the connection")
            s.send(xmlcommand)
            print("sent the xml")
            # data = s.recv(1024)
            # print(data)
    return HttpResponse("success")

# public String encodedString() {

# 		StringBuilder builder = new StringBuilder();
# 		builder.append("(");
# 		for (Entry<String, String> entry : map.entrySet()) {
# 			builder.append(entry.getKey() + delim + entry.getValue() + delim);   delim = "$$$";
# 		}
# 		// X$$$Y$$$, minimum
# 		builder.append(")");
# 		return builder.toString();
# 	}

    #  PrintStream writer = new PrintStream(socket.getOutputStream()) writer.println(kvpair.encodedstring) writer.flush()
    #  socket.write('(Scope$$$SIS.Scope1$$$\
    #             MessageType$$$Alert$$$\
    #             Sender$$$GUI$$$\
    #             Receiver$$$InterfaceServer$$$\
    #             MessageCode$$$701$$$\
    #             From$$$mike$$$\
    #             Subject$$$Request Report$$$\
    #             Body$$$Passcode:m1k3+b3n$$$)\n');
    # socket.on('data', (data) => {
    #     try{
    #         var entries = data.split('$$$');
    #         var confirmIndex = _.findIndex(entries, (entry) => { return entry === '3'})
    #         var serverIndex = _.findIndex(entries, (entry) => { return entry === 'InterfaceServer'})
    #         var statusIndex = _.findIndex(entries, (entry) => { return entry === 'Status'})
    #         var valuesIndex = _.findIndex(entries, (entry) => { return entry === 'Values'})
    #         if(confirmIndex !== -1 && serverIndex !== -1 && statusIndex !== -1 && valuesIndex != -1){
    #             var posterInfo = entries[valuesIndex + 1]
    #             var candidates = posterInfo.split(';')
    #             var returnCandidates = []
    #             _.forEach(candidates, (candidate) => {
    #                 var elements = candidate.split(',');
    #                 returnCandidates.push({
    #                     'name': elements[0],
    #                     'total': elements[1]
    #                 })
    #             });
    #             finalTotals = returnCandidates
    #             res.json({
    #                 Candidates: returnCandidates
    #             });
    #         }
    #     } catch(e){
    #         console.log(e);
    #     }