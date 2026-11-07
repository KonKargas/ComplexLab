document.addEventListener('DOMContentLoaded', () => {
  const operation = document.getElementById('operation');
  const matrixB = document.getElementById('matrixBPanel');
  const power = document.getElementById('powerOptions');
  const scalar = document.getElementById('scalarOptions');
  const random = document.getElementById('randomOptions');
  const usesB = new Set(['ADD', 'SUBTRACT', 'MULTIPLY']);
  function sync() {
    const op = operation.value;
    matrixB.hidden = !usesB.has(op);
    power.hidden = op !== 'POWER';
    scalar.hidden = op !== 'SCALAR_MULTIPLY';
    random.hidden = op !== 'RANDOM';
  }
  operation.addEventListener('change', sync);
  sync();
});
