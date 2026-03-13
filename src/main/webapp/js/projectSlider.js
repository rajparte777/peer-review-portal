/* =========================
   SLIDER FUNCTION
========================= */
function slide(button, direction) {
    const slider = button.closest(".slider");
    const track = slider.querySelector(".slider-track");
    const item = track.querySelector(".media-item");

    if (!item) return;

    const width = item.offsetWidth + 10; // 10 = gap safety

    track.scrollBy({
        left: direction * width,
        behavior: "smooth"
    });

    setTimeout(() => {
        updateDots(slider);
    }, 350);
}


/* =========================
   UPDATE DOT INDICATOR
========================= */
function updateDots(slider) {
    const track = slider.querySelector(".slider-track");
    const items = slider.querySelectorAll(".media-item");
    const dots = slider.querySelectorAll(".dot");

    if (!items.length || !dots.length) return;

    const itemWidth = items[0].offsetWidth + 10;
    const index = Math.round(track.scrollLeft / itemWidth);

    dots.forEach(dot => dot.classList.remove("active"));

    if (dots[index]) {
        dots[index].classList.add("active");
    }
}


/* =========================
   CLICK DOT → MOVE SLIDE
========================= */
function goToSlide(slider, index) {
    const track = slider.querySelector(".slider-track");
    const items = slider.querySelectorAll(".media-item");

    if (!items.length) return;

    const itemWidth = items[0].offsetWidth + 10;

    track.scrollTo({
        left: index * itemWidth,
        behavior: "smooth"
    });

    setTimeout(() => {
        updateDots(slider);
    }, 350);
}


/* =========================
   IMAGE POPUP
========================= */
function openPopup(src) {
    const popup = document.getElementById("popup");
    const popupImg = document.getElementById("popupImg");

    if (!popup || !popupImg) return;

    popup.style.display = "flex";
    popupImg.src = src;
}


/* =========================
   CLOSE POPUP
========================= */
function closePopup() {
    const popup = document.getElementById("popup");
    const popupImg = document.getElementById("popupImg");

    if (!popup || !popupImg) return;

    popup.style.display = "none";
    popupImg.src = "";
}


/* =========================
   INITIALIZE SLIDERS
========================= */
document.addEventListener("DOMContentLoaded", function () {
    const sliders = document.querySelectorAll(".slider");

    sliders.forEach(slider => {
        const track = slider.querySelector(".slider-track");
        const items = slider.querySelectorAll(".media-item");
        const dotsContainer = slider.querySelector(".dots");
        const leftArrow = slider.querySelector(".arrow.left");
        const rightArrow = slider.querySelector(".arrow.right");

        if (!track || !dotsContainer) return;

        // Clear old dots first (important if page reload/forward/back)
        dotsContainer.innerHTML = "";

        // If only 0 or 1 media item, hide arrows and dots
        if (items.length <= 1) {
            if (leftArrow) leftArrow.style.display = "none";
            if (rightArrow) rightArrow.style.display = "none";
            dotsContainer.style.display = "none";
            return;
        }

        // Create dots
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

        // Update dots when user manually scrolls
        track.addEventListener("scroll", function () {
            updateDots(slider);
        });
    });

    /* =========================
       CLOSE POPUP ON OUTSIDE CLICK
    ========================= */
    const popup = document.getElementById("popup");

    if (popup) {
        popup.addEventListener("click", function (e) {
            if (e.target.id === "popup") {
                closePopup();
            }
        });
    }
});