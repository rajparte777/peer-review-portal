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