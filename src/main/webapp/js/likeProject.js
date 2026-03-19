/**
 * 
 */
function toggleLike(button) {
    const projectId = button.getAttribute("data-project-id");

    fetch("LikeServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "projectId=" + encodeURIComponent(projectId)
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === "success") {
            const likeCountSpan = document.getElementById("like-count-" + projectId);
            const likeText = button.querySelector(".like-text");

            likeCountSpan.textContent = data.likeCount;

            if (data.liked) {
                button.classList.add("liked");
                likeText.textContent = "💔 Unlike";
            } else {
                button.classList.remove("liked");
                likeText.textContent = "👍 Like";
            }
        } else {
            alert(data.message || "Unable to update like");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Something went wrong while updating like");
    });
}