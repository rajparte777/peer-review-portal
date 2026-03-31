function slide(button, direction) {
    const slider = button.closest(".slider");
    if (!slider) return;

    const track = slider.querySelector(".slider-track");
    if (!track) return;

    const item = track.querySelector(".media-item");
    if (!item) return;

    const width = item.offsetWidth + 10;

    track.scrollBy({
        left: direction * width,
        behavior: "smooth"
    });

    setTimeout(() => {
        updateDots(slider);
    }, 350);
}

function updateDots(slider) {
    const track = slider.querySelector(".slider-track");
    const items = slider.querySelectorAll(".media-item");
    const dots = slider.querySelectorAll(".dot");

    if (!track || !items.length || !dots.length) return;

    const itemWidth = items[0].offsetWidth + 10;
    const index = Math.round(track.scrollLeft / itemWidth);

    dots.forEach(dot => dot.classList.remove("active"));

    if (dots[index]) {
        dots[index].classList.add("active");
    }
}


function updatePopupArrows() {
    const prevBtn = document.querySelector(".popup-prev");
    const nextBtn = document.querySelector(".popup-next");

    if (!prevBtn || !nextBtn) return;

    prevBtn.style.display = popupIndex === 0 ? "none" : "flex";
    nextBtn.style.display = popupIndex === popupMediaItems.length - 1 ? "none" : "flex";
}
function goToSlide(slider, index) {
    const track = slider.querySelector(".slider-track");
    const items = slider.querySelectorAll(".media-item");

    if (!track || !items.length) return;

    const itemWidth = items[0].offsetWidth + 10;

    track.scrollTo({
        left: index * itemWidth,
        behavior: "smooth"
    });

    setTimeout(() => {
        updateDots(slider);
    }, 350);
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

    if (popupIndex === -1) return;

    showPopupItem(popupIndex);

    const popup = document.getElementById("popup");
    if (popup) {
        popup.style.display = "flex";
    }
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
	updatePopupArrows();
}

function slidePopup(direction) {
    if (!popupMediaItems.length) return;

    const newIndex = popupIndex + direction;

    if (newIndex < 0 || newIndex >= popupMediaItems.length) {
        return; // stop at first and last image
    }

    popupIndex = newIndex;
    showPopupItem(popupIndex);
}

function closePopup() {
    const popup = document.getElementById("popup");
    const popupImg = document.getElementById("popupImg");
    const popupVideo = document.getElementById("popupVideo");

    if (popup) {
        popup.style.display = "none";
    }

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

document.addEventListener("DOMContentLoaded", function () {
    const sliders = document.querySelectorAll(".slider");

    sliders.forEach(slider => {
        const track = slider.querySelector(".slider-track");
        const items = slider.querySelectorAll(".media-item");
        const dotsContainer = slider.querySelector(".dots");
        const leftArrow = slider.querySelector(".arrow.left");
        const rightArrow = slider.querySelector(".arrow.right");

        if (!track || !dotsContainer) return;

        dotsContainer.innerHTML = "";

        if (items.length <= 1) {
            if (leftArrow) leftArrow.style.display = "none";
            if (rightArrow) rightArrow.style.display = "none";
            dotsContainer.style.display = "none";
            return;
        }

        items.forEach((item, i) => {
            const dot = document.createElement("span");
            dot.classList.add("dot");

            if (i === 0) {
                dot.classList.add("active");
            }

            dot.addEventListener("click", function () {
                goToSlide(slider, i);
            });

            dotsContainer.appendChild(dot);
        });

        track.addEventListener("scroll", function () {
            updateDots(slider);
        });
    });

    const popup = document.getElementById("popup");

    if (popup) {
        popup.addEventListener("click", function (e) {
            if (e.target.id === "popup") {
                closePopup();
            }
        });
    }
});