function loadData() {
    fetch("viewPage").then(function json(res) {
        if (res.status != 200) {
            document.getElementById("info").textContent = "no data the status code is " + res.status;
        }
        else {
            res.json().then(function(data) {

                document.getElementById("lastTemp").textContent = data.lastTemp;
                document.getElementById("status").textContent = data.serviceStatus;
                document.getElementById("status").style.fontSize = "30px";
                if (data.serviceStatus == "Stopped") {

                    document.getElementById("status").style.color = "red";
                }
                else {
               
                    document.getElementById("status").style.color = "green";
                }
                document.getElementById("failedAttempts").textContent = data.failedAttempts
                document.getElementById("lastTime").textContent = data.lastTime
                if (data.lastError != null)
                    document.getElementById("lastError").textContent = data.lastError

                else
                    document.getElementById("lastError").textContent = "no error in the last attempts";

                document.getElementById("failedAttempts").textContent = data.failedAttempts
                document.getElementById("last10Reads").textContent = "";
                for (var i = 0;i < data.last10Reads.length;i++) {
                    document.getElementById("last10Reads").innerHTML += "<li>" + data.last10Reads[i].time + " - " + data.last10Reads[i].temp + "</li>";
                }


            }
            )
        }
    })
}

loadData();
setInterval(loadData, 10000);