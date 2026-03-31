function openFeedbackSection(sectionId) {
    const targetSection = document.getElementById(sectionId);
    if (!targetSection) return;

    const projectCard = targetSection.closest(".project-card");
    if (!projectCard) return;

    const feedbackSections = projectCard.querySelectorAll(
        ".project-section[id^='comments-'], .project-section[id^='reviews-']"
    );

    const isOpen = !targetSection.classList.contains("collapsed");

    feedbackSections.forEach(section => {
        section.classList.add("collapsed");
    });

    if (!isOpen) {
        targetSection.classList.remove("collapsed");
        targetSection.scrollIntoView({
            behavior: "smooth",
            block: "start"
        });
    }
}
function openProjectSection(sectionId) {
    const targetSection = document.getElementById(sectionId);
    if (!targetSection) return;

    const projectCard = targetSection.closest(".project-card");
    if (!projectCard) return;

    const projectSections = projectCard.querySelectorAll(
        ".project-section[id^='comments-'], .project-section[id^='reviews-']"
    );

    const isOpen = !targetSection.classList.contains("collapsed");

    projectSections.forEach(section => {
        section.classList.add("collapsed");
    });

    if (!isOpen) {
        targetSection.classList.remove("collapsed");
        targetSection.scrollIntoView({
            behavior: "smooth",
            block: "start"
        });
    }
}

let popupMediaItems = [];
let popupIndex = 0;

function openProjectPopup(clickedItem) {
    const slider = clickedItem.closest(".slider");
    if (!slider) return;

    const sliderTrack = slider.querySelector(".slider-track");
    if (!sliderTrack) return;

    popupMediaItems = Array.from(sliderTrack.querySelectorAll(".media-item"));
    popupIndex = popupMediaItems.indexOf(clickedItem);

    document.getElementById("popup").style.display = "flex";
    showPopupItem(popupIndex);
}

function showPopupItem(index) {
    const popupImg = document.getElementById("popupImg");
    const popupVideo = document.getElementById("popupVideo");

    if (!popupImg || !popupVideo) return;
    if (!popupMediaItems.length) return;

    const currentItem = popupMediaItems[index];
    if (!currentItem) return;

    popupImg.style.display = "none";
    popupVideo.style.display = "none";

    popupImg.removeAttribute("src");
    popupVideo.pause();
    popupVideo.removeAttribute("src");

    if (currentItem.tagName.toLowerCase() === "img") {
        popupImg.src = currentItem.src;
        popupImg.style.display = "block";
    } else if (currentItem.tagName.toLowerCase() === "video") {
        const source = currentItem.querySelector("source");
        if (source && source.src) {
            popupVideo.src = source.src;
            popupVideo.style.display = "block";
            popupVideo.load();
        }
    }
}

function slidePopup(direction) {
    if (!popupMediaItems.length) return;

    popupIndex += direction;

    if (popupIndex < 0) {
        popupIndex = popupMediaItems.length - 1;
    } else if (popupIndex >= popupMediaItems.length) {
        popupIndex = 0;
    }

    showPopupItem(popupIndex);
}

function closePopup() {
    const popup = document.getElementById("popup");
    const popupImg = document.getElementById("popupImg");
    const popupVideo = document.getElementById("popupVideo");

    if (popup) popup.style.display = "none";

    if (popupImg) {
        popupImg.removeAttribute("src");
        popupImg.style.display = "none";
    }

    if (popupVideo) {
        popupVideo.pause();
        popupVideo.removeAttribute("src");
        popupVideo.style.display = "none";
    }
}