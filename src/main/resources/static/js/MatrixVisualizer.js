document.addEventListener('DOMContentLoaded', () => {
    'use strict';
    const $ = id => document.getElementById(id);
    const input = $('matrixInput'), mode = $('matrixMode'), grid = $('matrixHeatmap');
    const error = $('matrixVizError'), canvas = $('matrixComplexPlane');
    if (!input || !mode || !grid || !canvas) return;
    const ctx = canvas.getContext('2d');
    const example = '1:2 3:0 0:-1\n2:-1 4:3 -2:0\n0:2 1:-2 3:4';
    let matrix = [], selected = [0, 0];
    const fmt = number => Number(number.toFixed(3)).toString();
    const label = z => `${fmt(z.r)} ${z.i < 0 ? '−' : '+'} ${fmt(Math.abs(z.i))}i`;
    const metric = z => {
        switch (mode.value) {
            case 'real': return z.r;
            case 'imaginary': return z.i;
            case 'phase': return Math.atan2(z.i, z.r) * 180 / Math.PI;
            default: return Math.hypot(z.r, z.i);
        }
    };
    function parse(text) {
        if (!text || !text.trim()) throw Error('Please enter a matrix.');
        const rows = text.trim().split(/\r\n|\n|\r/);
        if (rows.length > 10) throw Error('Maximum 10 rows allowed.');
        let columns = 0;
        return rows.map((line, row) => {
            const cells = line.trim().split(/\s+/);
            if (row === 0) columns = cells.length;
            if (columns > 10) throw Error('Maximum 10 columns allowed.');
            if (cells.length !== columns) throw Error(`Row ${row + 1} has a different number of columns.`);
            return cells.map((cell, col) => {
                const parts = cell.split(':');
                if (parts.length !== 2 || parts.some(part => part.trim() === ''))
                    throw Error(`Invalid value at row ${row + 1}, column ${col + 1}. Use real:imaginary.`);
                const numbers = parts.map(Number);
                if (!numbers.every(Number.isFinite)) throw Error(`Invalid number at row ${row + 1}, column ${col + 1}.`);
                return {r: numbers[0], i: numbers[1]};
            });
        });
    }
    function line(x1,y1,x2,y2,color,width=1) {
        ctx.beginPath(); ctx.strokeStyle=color; ctx.lineWidth=width;
        ctx.moveTo(x1,y1); ctx.lineTo(x2,y2); ctx.stroke();
    }
    function drawPlane(z) {
        const rect=canvas.getBoundingClientRect();
        if (!rect.width || !rect.height) return;
        const dpr=window.devicePixelRatio || 1;
        canvas.width=Math.round(rect.width*dpr); canvas.height=Math.round(rect.height*dpr);
        ctx.setTransform(dpr,0,0,dpr,0,0);
        const w=rect.width,h=rect.height,cx=w/2,cy=h/2;
        ctx.clearRect(0,0,w,h);
        const extent=Math.max(3,Math.ceil(Math.max(Math.abs(z.r),Math.abs(z.i)) + 1));
        const scale=Math.min(w,h)/(2*extent+1);
        const step=Math.max(1,Math.ceil(extent/8));
        for(let n=-extent;n<=extent;n+=step) {
            line(cx+n*scale,0,cx+n*scale,h,'#26354b');
            line(0,cy+n*scale,w,cy+n*scale,'#26354b');
        }
        line(0,cy,w,cy,'#8293ad',1.4);line(cx,0,cx,h,'#8293ad',1.4);
        ctx.fillStyle='#c4d3e8'; ctx.font='12px Segoe UI';
        ctx.fillText('Re',w-24,cy-8);ctx.fillText('Im',cx+8,14);
        const x=cx+z.r*scale,y=cy-z.i*scale;
        line(cx,cy,x,y,'#60a5fa',3);
        ctx.beginPath();ctx.arc(x,y,5,0,Math.PI*2);ctx.fillStyle='#60a5fa';ctx.fill();
        ctx.fillStyle='#e2e8f0';ctx.font='bold 12px Segoe UI';
        const tx=x>w-85?x-75:x+10,ty=y<22?y+20:y-10;
        ctx.fillText('z = '+label(z),Math.max(5,tx),Math.max(14,ty));
    }
    function showSelected() {
        if (!matrix.length) return;
        const [r,c]=selected, z=matrix[r][c];
        $('selectedPosition').textContent=`A[${r+1},${c+1}]`;
        $('selectedValue').textContent=label(z);
        $('selectedReal').textContent=fmt(z.r);
        $('selectedImaginary').textContent=fmt(z.i);
        $('selectedMagnitude').textContent=fmt(Math.hypot(z.r,z.i));
        $('selectedArgument').textContent=fmt(Math.atan2(z.i,z.r)*180/Math.PI)+'°';
        drawPlane(z);
    }
    function render() {
        grid.replaceChildren();
        const values=matrix.flat().map(metric), min=Math.min(...values), max=Math.max(...values);
        const range=max-min;
        $('matrixDimensions').textContent=`${matrix.length} × ${matrix[0].length}`;
        $('legendMin').textContent=fmt(min);$('legendMax').textContent=fmt(max);
        const signed = mode.value !== 'magnitude';
        $('matrixLegend').style.background=signed
            ? 'linear-gradient(90deg,#d97735,#283e5e,#60a5fa)'
            : 'linear-gradient(90deg,#172944,#265fa5,#60a5fa,#c4e5ff)';
        $('heatmapDescription').textContent=signed
            ? 'Amber = smaller values, blue = larger values (relative to this matrix).'
            : 'Brighter blue = larger magnitude (relative to this matrix).';
        const count=matrix[0].length;
        grid.style.gridTemplateColumns=`repeat(${count}, minmax(78px, 1fr))`;
        matrix.forEach((row,r) => row.forEach((z,c) => {
            const value=metric(z), t=range===0?0.5:(value-min)/range;
            const button=document.createElement('button');button.type='button';
            button.className=selected[0]===r&&selected[1]===c?'selected':'';
            button.style.background=signed
                ? `hsl(${28+t*183} 62% ${25+t*13}%)`
                : `hsl(214 64% ${17+t*28}%)`;
            button.setAttribute('aria-label',`Row ${r+1}, column ${c+1}: ${label(z)}. ${mode.options[mode.selectedIndex].text}: ${fmt(value)}`);
            const complex=document.createElement('span');complex.className='cell-complex';complex.textContent=label(z);
            const metricText=document.createElement('span');metricText.className='cell-value';metricText.textContent=fmt(value)+(mode.value==='phase'?'°':'');
            button.append(complex,metricText);
            button.addEventListener('click',()=>{selected=[r,c];render();});
            grid.append(button);
        }));
        showSelected();
    }
    function update() {
        try {
            matrix=parse(input.value);
            if(selected[0]>=matrix.length || selected[1]>=matrix[0].length) selected=[0,0];
            error.hidden=true; render();
        } catch(e) {
            matrix=[];grid.replaceChildren();error.hidden=false;error.textContent=e.message;
            $('matrixDimensions').textContent='—';
            $('selectedPosition').textContent='—';$('selectedValue').textContent='—';
            ['selectedReal','selectedImaginary','selectedMagnitude','selectedArgument'].forEach(id=>$(id).textContent='—');
            const rect=canvas.getBoundingClientRect();ctx.clearRect(0,0,rect.width,rect.height);
        }
    }
    input.addEventListener('input',update);
    mode.addEventListener('change',update);
    $('matrixSample').addEventListener('click',()=>{input.value=example;selected=[0,0];update();});
    $('matrixClear').addEventListener('click',()=>{input.value='0:0 0:0\n0:0 0:0';selected=[0,0];update();});
    window.addEventListener('resize',()=>{if(matrix.length)showSelected();});
    update();
});
