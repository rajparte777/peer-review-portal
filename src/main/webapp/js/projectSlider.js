/* =========================
   SLIDER FUNCTION
========================= */
const slide = (button, direction) => {
    const slider = button.parentElement;
    const track = slider.querySelector(".slider-track");
    const width = slider.offsetWidth;

    track.scrollBy({
        left: direction * width,
        behavior: "smooth"
    });

    setTimeout(() => updateDots(slider), 300);
};


/* =========================
   UPDATE DOT INDICATOR
========================= */
const updateDots = slider => {
    const track = slider.querySelector(".slider-track");
    const dots = slider.querySelectorAll(".dot");
    const width = slider.offsetWidth;

    const index = Math.round(track.scrollLeft / width);

    dots.forEach(dot => dot.classList.remove("active"));

    if (dots[index]) dots[index].classList.add("active");
};


/* =========================
   CLICK DOT → MOVE SLIDE
========================= */
const goToSlide = (slider, index) => {
    const track = slider.querySelector(".slider-track");
    const width = slider.offsetWidth;

    track.scrollTo({
        left: index * width,
        behavior: "smooth"
    });

    updateDots(slider);
};


/* =========================
   IMAGE POPUP
========================= */
const openPopup = src => {
    const popup = document.getElementById("popup");
    const popupImg = document.getElementById("popupImg");

    popup.style.display = "flex";
    popupImg.src = src;
};


/* =========================
   CLOSE POPUP
========================= */
const closePopup = () => {
    const popup = document.getElementById("popup");
    popup.style.display = "none";
};


/* =========================
   INITIALIZE DOTS
========================= */
document.addEventListener("DOMContentLoaded", () => {
    const sliders = document.querySelectorAll(".slider");

    sliders.forEach(slider => {
        const track = slider.querySelector(".slider-track");
        const items = track.children;
        const dotsContainer = slider.querySelector(".dots");

        if (!dotsContainer) return;

        Array.from(items).forEach((_, i) => {
            const dot = document.createElement("span");
            dot.classList.add("dot");
            if (i === 0) dot.classList.add("active");

            // Use closure to safely capture the slider and index
            dot.addEventListener("click", () => {
                goToSlide(slider, i);
            });

            dotsContainer.appendChild(dot);
        });
    });
});