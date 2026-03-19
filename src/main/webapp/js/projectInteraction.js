function submitComment(event, form) {
    event.preventDefault();

    const formData = new FormData(form);
    const projectId = formData.get("projectId");
    const comment = formData.get("comment");

    fetch("addComment", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "projectId=" + encodeURIComponent(projectId) +
              "&comment=" + encodeURIComponent(comment)
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === "success") {
            const commentList = document.getElementById("comment-list-" + projectId);
            const commentCount = document.getElementById("comment-count-" + projectId);

            const noComment = commentList.querySelector(".no-comment");
            if (noComment) {
                noComment.remove();
            }

            const newComment = document.createElement("div");
            newComment.className = "comment-box";
            newComment.innerHTML = "<p><b>" + data.userEmail + "</b></p><p>" + data.comment + "</p>";

            commentList.prepend(newComment);
            commentCount.textContent = data.commentCount;
            form.reset();
        } else {
            alert(data.message || "Unable to add comment");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Something went wrong while adding comment");
    });
}

function submitReview(event, form) {
    event.preventDefault();

    const formData = new FormData(form);
    const projectId = formData.get("projectId");
    const rating = formData.get("rating");
    const reviewText = formData.get("reviewText");

    fetch("addReview", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "projectId=" + encodeURIComponent(projectId) +
              "&rating=" + encodeURIComponent(rating) +
              "&reviewText=" + encodeURIComponent(reviewText)
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === "success") {
            const reviewList = document.getElementById("review-list-" + projectId);
            const reviewCount = document.getElementById("review-count-" + projectId);
            const avgRating = document.getElementById("avg-rating-" + projectId);

            reviewList.innerHTML = "";

            data.reviews.forEach(review => {
                const reviewBox = document.createElement("div");
                reviewBox.className = "review-box";
                reviewBox.innerHTML =
                    "<p><b>" + review.reviewerEmail + "</b> - " + review.rating + "/5</p>" +
                    "<p>" + review.reviewText + "</p>";

                reviewList.appendChild(reviewBox);
            });

            reviewCount.textContent = data.reviewCount;
            avgRating.textContent = data.avgRating;
            form.reset();
        } else {
            alert(data.message || "Unable to submit review");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Something went wrong while submitting review");
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const sections = document.querySelectorAll(".project-section");

    sections.forEach(section => {
        const heading = section.querySelector("h3");
        if (!heading) return;

        section.classList.add("collapsed");

        heading.addEventListener("click", function () {
            section.classList.toggle("collapsed");
        });
    });
});