function toggleSection(id) {
    let commentsBox = document.getElementById("comments-" + id.split("-")[1]);
    let reviewsBox = document.getElementById("reviews-" + id.split("-")[1]);

    let current = document.getElementById(id);

    if (current.classList.contains("show-section")) {
        current.classList.remove("show-section");
    } else {
        if (commentsBox) commentsBox.classList.remove("show-section");
        if (reviewsBox) reviewsBox.classList.remove("show-section");
        current.classList.add("show-section");
    }
}
function toggleDescription(projectId) {
    const shortDesc = document.getElementById("short-desc-" + projectId);
    const fullDesc = document.getElementById("full-desc-" + projectId);
    const toggleBtn = document.getElementById("toggle-btn-" + projectId);

    if (!shortDesc || !fullDesc || !toggleBtn) return;

    if (fullDesc.style.display === "none") {
        fullDesc.style.display = "inline";
        shortDesc.style.display = "none";
        toggleBtn.innerText = "Show Less";
    } else {
        fullDesc.style.display = "none";
        shortDesc.style.display = "inline";
        toggleBtn.innerText = "Read More";
    }
}