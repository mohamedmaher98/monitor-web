var box = document.getElementById("servers");
var tooltip = document.getElementById("tooltip");
var tooltipHead = document.getElementById("tooltip-head");
var tooltipDesc = document.getElementById("tooltip-desc");

fetch("MonitorServiceServlet")
    .then(function (response) {
        if (!response.ok) {
            throw new Error("server returned " + response.status);
        }
        return response.json();
    })
    .then(function (servers) {
        if (servers.length === 0) {
            box.innerHTML = "<div class='message'>no servers registered</div>";
            return;
        }
        box.innerHTML = buildServers(servers);
        wireTooltips(servers);
    })
    .catch(function (err) {
        box.innerHTML = "<div class='message error'>could not load: " + err.message + "</div>";
    });

function buildServers(servers) {
    var html = "";
    for (var i = 0; i < servers.length; i++) {
        var server = servers[i];
        var dotClass = server.active ? "dot active" : "dot inactive";
        html += "<div class='server'>"
              +   "<div class='server-head'>"
              +     "<div class='" + dotClass + "'></div>"
              +     "<div class='server-name'>" + escapeHtml(server.name) + "</div>"
              +     "<div class='server-url'>" + escapeHtml(server.url) + "</div>"
              +   "</div>"
              +   "<div class='cards'>" + buildCards(server) + "</div>"
              + "</div>";
    }
    return html;
}

function buildCards(server) {
    var html = "";
    var services = server.services || [];
    for (var j = 0; j < services.length; j++) {
        var service = services[j];
        html += "<div class='card'>"
              +   "<div>"
              +     "<div class='card-name'>" + escapeHtml(service.name) + "</div>"
              +     "<div class='card-key'>" + escapeHtml(service.service_key) + "</div>"
              +   "</div>"
              +   "<div class='info-icon' data-service-id='" + service.id + "'>i</div>"
              + "</div>";
    }
    return html;
}

function wireTooltips(servers) {
    var byId = {};
    for (var i = 0; i < servers.length; i++) {
        var services = servers[i].services || [];
        for (var j = 0; j < services.length; j++) {
            byId[services[j].id] = services[j];
        }
    }

    var icons = document.getElementsByClassName("info-icon");
    for (var k = 0; k < icons.length; k++) {
        icons[k].addEventListener("mouseenter", function (event) {
            var service = byId[this.getAttribute("data-service-id")];
            if (!service) {
                return;
            }
            tooltipHead.textContent = service.name;
            tooltipDesc.textContent = service.description || "no description";
            tooltip.style.display = "block";
            moveTooltip(event);
        });
        icons[k].addEventListener("mousemove", moveTooltip);
        icons[k].addEventListener("mouseleave", function () {
            tooltip.style.display = "none";
        });
    }
}

function moveTooltip(event) {
    tooltip.style.left = (event.clientX + 14) + "px";
    tooltip.style.top = (event.clientY + 14) + "px";
}

function escapeHtml(text) {
    if (text === null || text === undefined) {
        return "";
    }
    return String(text)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
}
