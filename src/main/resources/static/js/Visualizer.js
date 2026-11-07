document.addEventListener('DOMContentLoaded', () => {
  const canvas = document.getElementById('complexPlane');
  if (!canvas) return;
  const ctx = canvas.getContext('2d');
  const field = id => document.getElementById(id);
  const inputs = ['aReal','aImag','bReal','bImag'].map(field);
  const operation = field('showOperation');
  const zoom = field('zoom');
  const scaleValue = field('scaleValue');
  const results = field('vizResults');
  const error = field('vizError');
  let dragging = null;
  let side = 6, pixelScale = 30, centerX = 0, centerY = 0;
  const read = input => input.value.trim() === '' ? NaN : Number(input.value);
  const format = n => Number(n.toFixed(3)).toString();
  const show = (z) => `${format(z[0])} ${z[1] < 0 ? '−' : '+'} ${format(Math.abs(z[1]))}i`;
  const magnitude = z => Math.hypot(...z);
  const values = () => [[read(inputs[0]),read(inputs[1])],[read(inputs[2]),read(inputs[3])]];
  const toScreen = z => [centerX + z[0]*pixelScale, centerY-z[1]*pixelScale];
  const fromScreen = (x,y) => [(x-centerX)/pixelScale,(centerY-y)/pixelScale];

  function line(x1,y1,x2,y2,color,width=1){ctx.beginPath();ctx.strokeStyle=color;ctx.lineWidth=width;ctx.moveTo(x1,y1);ctx.lineTo(x2,y2);ctx.stroke();}
  function vector(z,color,name){
    const [x,y]=toScreen(z);const theta=Math.atan2(y-centerY,x-centerX);
    line(centerX,centerY,x,y,color,2.6);
    ctx.beginPath();ctx.fillStyle=color;ctx.arc(x,y,5,0,Math.PI*2);ctx.fill();
    ctx.fillStyle=color;ctx.font='bold 13px Segoe UI';ctx.fillText(name,x+10,y-10);
    if(magnitude(z)>.2){line(x,y,x-13*Math.cos(theta-.4),y-13*Math.sin(theta-.4),color,2.6);
      line(x,y,x-13*Math.cos(theta+.4),y-13*Math.sin(theta+.4),color,2.6);}
  }
  function resize(){
    const rect=canvas.getBoundingClientRect();
    const dpr=window.devicePixelRatio||1;
    canvas.width=Math.round(rect.width*dpr);canvas.height=Math.round(rect.height*dpr);
    ctx.setTransform(dpr,0,0,dpr,0,0);
    centerX=rect.width/2;centerY=rect.height/2;
    pixelScale=Math.min(rect.width,rect.height)/(2*side+2);
  }
  function draw(){
    side=Number(zoom.value);scaleValue.textContent=String(side);resize();
    const [a,b]=values();
    const valid=[...a,...b].every(Number.isFinite);
    error.hidden=valid;
    if(!valid){error.textContent='Enter valid finite real and imaginary values.';results.textContent='';}
    ctx.clearRect(0,0,canvas.clientWidth,canvas.clientHeight);
    for(let i=-side;i<=side;i++){
      const x=centerX+i*pixelScale,y=centerY+i*pixelScale;
      line(x,0,x,canvas.clientHeight,'#26354b',1);
      line(0,y,canvas.clientWidth,y,'#26354b',1);
      if(i!==0){ctx.fillStyle='#7588a8';ctx.font='10px Segoe UI';ctx.fillText(String(i),x+2,centerY+13);ctx.fillText(String(-i),centerX+5,y-3);}
    }
    line(0,centerY,canvas.clientWidth,centerY,'#8293ad',1.5);
    line(centerX,0,centerX,canvas.clientHeight,'#8293ad',1.5);
    ctx.fillStyle='#c4d3e8';ctx.font='12px Segoe UI';ctx.fillText('Re',canvas.clientWidth-25,centerY-9);ctx.fillText('Im',centerX+10,15);
    if(!valid) return;
    vector(a,'#60a5fa','A');vector(b,'#fbbf24','B');
    let result=null;
    if(operation.value==='add') result=[a[0]+b[0],a[1]+b[1]];
    if(operation.value==='subtract') result=[a[0]-b[0],a[1]-b[1]];
    if(operation.value==='multiply') result=[a[0]*b[0]-a[1]*b[1],a[0]*b[1]+a[1]*b[0]];
    if(result && result.every(Number.isFinite)) vector(result,'#34d399','Result');
    const entry=(label,z,color)=>`<div class="viz-stat"><strong style="color:${color}">${label}</strong><div>${show(z)}</div><div>|z| = ${format(magnitude(z))}</div><div>arg = ${format(Math.atan2(z[1],z[0])*180/Math.PI)}°</div></div>`;
    results.innerHTML=entry('A',a,'#60a5fa')+entry('B',b,'#fbbf24')+(result && result.every(Number.isFinite)?entry('Result',result,'#34d399'):'');
  }
  function pointerPosition(e){const r=canvas.getBoundingClientRect();return [e.clientX-r.left,e.clientY-r.top];}
  canvas.addEventListener('pointerdown',e=>{
    if(!values().flat().every(Number.isFinite)) return;
    const point=pointerPosition(e),[a,b]=values();
    const distance=z=>Math.hypot(point[0]-toScreen(z)[0],point[1]-toScreen(z)[1]);
    if(Math.min(distance(a),distance(b))<24){dragging=distance(a)<=distance(b)?0:1;canvas.setPointerCapture(e.pointerId);}
  });
  canvas.addEventListener('pointermove',e=>{
    if(dragging===null) return;
    const [x,y]=pointerPosition(e),z=fromScreen(x,y);
    inputs[dragging*2].value=String(Number(z[0].toFixed(2)));
    inputs[dragging*2+1].value=String(Number(z[1].toFixed(2)));
    draw();
  });
  const stop=()=>{dragging=null;};
  canvas.addEventListener('pointerup',stop);canvas.addEventListener('pointercancel',stop);
  [...inputs,operation,zoom].forEach(el=>el.addEventListener('input',draw));
  field('resetView').addEventListener('click',()=>{['3','2','1','-4'].forEach((v,i)=>inputs[i].value=v);zoom.value='6';operation.value='add';draw();});
  window.addEventListener('resize',draw);
  draw();
});
