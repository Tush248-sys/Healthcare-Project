document.addEventListener('DOMContentLoaded', () => {
  const steps = Array.from(document.querySelectorAll('[data-step]'));
  const pills = Array.from(document.querySelectorAll('.join-step-pill'));
  let current = 0;

  function showStep(index) {
    steps.forEach((step, i) => {
      step.style.display = i === index ? 'block' : 'none';
    });
    pills.forEach((pill, i) => {
      pill.classList.toggle('active', i === index);
    });
    current = index;
  }

  document.querySelectorAll('[data-next]').forEach((btn) => {
    btn.addEventListener('click', (e) => {
      e.preventDefault();
      if (current < steps.length - 1) {
        showStep(current + 1);
      }
    });
  });

  document.querySelectorAll('[data-prev]').forEach((btn) => {
    btn.addEventListener('click', (e) => {
      e.preventDefault();
      if (current > 0) {
        showStep(current - 1);
      }
    });
  });

  const addDeptBtn = document.getElementById('addDepartmentRow');
  if (addDeptBtn) {
    addDeptBtn.addEventListener('click', (e) => {
      e.preventDefault();
      const tableBody = document.getElementById('departmentsBody');
      if (!tableBody) return;
      const index = tableBody.children.length;
      const row = document.createElement('tr');
      row.innerHTML = `
        <td><input name="departments[${index}].departmentName" /></td>
        <td><input name="departments[${index}].opdMorningTimings" /></td>
        <td><input name="departments[${index}].opdEveningTimings" /></td>
        <td><input name="departments[${index}].weeklyOffDays" /></td>
        <td><input type="number" min="0" name="departments[${index}].maxPatientsPerShift" /></td>
      `;
      tableBody.appendChild(row);
    });
  }

  // Initialise first step
  if (steps.length > 0) {
    showStep(0);
  }
});
