// Carousel functionality
let currentSlide = 0;
const slides = [
  {
    title: 'Ayushman Bharat Health Account',
    description:
      'ABHA - Ayushman Bharat Health Account is the first step towards creating safer and efficient digital health records for you and your family.',
    image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?w=600&q=80',
  },
  {
    title: 'Online Appointment Booking',
    description:
      'Book appointments online for OPD consultations at government hospitals across India with ease.',
    image: 'https://images.unsplash.com/photo-1631217868264-e5b90bb7e133?w=600&q=80',
  },
  {
    title: 'Teleconsultation Services',
    description:
      'Access healthcare from home with our e-OPD and teleconsultation features.',
    image: 'https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?w=600&q=80',
  },
];

function updateCarousel() {
  const titleEl = document.getElementById('carouselTitle');
  const descEl = document.getElementById('carouselDescription');
  const imgEl = document.getElementById('carouselImage');

  if (!titleEl || !descEl || !imgEl) return;

  titleEl.textContent = slides[currentSlide].title;
  descEl.textContent = slides[currentSlide].description;
  imgEl.src = slides[currentSlide].image;

  // Update indicators
  const indicators = document.querySelectorAll('.indicator');
  indicators.forEach((indicator, index) => {
    indicator.classList.toggle('active', index === currentSlide);
  });
}

function nextSlide() {
  currentSlide = (currentSlide + 1) % slides.length;
  updateCarousel();
}

function prevSlide() {
  currentSlide = (currentSlide - 1 + slides.length) % slides.length;
  updateCarousel();
}

function goToSlide(index) {
  currentSlide = index;
  updateCarousel();
}

// Auto-advance carousel
setInterval(nextSlide, 5000);

// Counter animation
function animateCounter(id, targetValue) {
  const element = document.getElementById(id);
  if (!element) return;

  const duration = 2000;
  const steps = 60;
  const increment = targetValue / steps;
  let current = 0;

  const timer = setInterval(() => {
    current += increment;
    if (current >= targetValue) {
      current = targetValue;
      clearInterval(timer);
    }
    element.textContent = Math.floor(current).toLocaleString();
  }, duration / steps);
}

// Start counters when page loads
window.addEventListener('load', () => {
  animateCounter('hospitalsCount', 2405);
  animateCounter('todayCount', 293);
  animateCounter('totalCount', 12518101);
});

// Smooth scroll for anchor links
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
    anchor.addEventListener('click', function (e) {
      const href = this.getAttribute('href');
      if (!href || href === '#') return;

      e.preventDefault();
      const target = document.querySelector(href);
      if (target) {
        target.scrollIntoView({ behavior: 'smooth' });
      }
    });
  });
});
