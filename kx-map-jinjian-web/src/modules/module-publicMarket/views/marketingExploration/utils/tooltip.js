/**
 * 绘制提示框tooltips
 * @param text 文字内容
 * @param fontsize 字体大小
 * @param fontcolor 字体颜色
 * @param bgcolor 背景颜色
 * todo 框子高度默认为36，后面还可以拓展，做换行处理
 */
export const getToolTip = (text, fontsize, fontcolor, bgcolor) => {
  let canvas = document.createElement("canvas");
  // let canvas = document.getElementById("canvas");
  let width = getCanvasWidth(text, fontsize);
  let boundWidth = getCanvasWidth(text, fontsize) + 32; //两边间距16+16
  canvas.width = boundWidth;
  canvas.height = 36;
  let context = canvas.getContext("2d");
  //画框子
  fillRoundRect(context, 0, 0, boundWidth, 30, 5, bgcolor); //rgba(0,0,0,0.7)

  context.font = fontsize + "px Arial";
  context.textBaseline = "middle";
  context.fillStyle = fontcolor;

  let sw = boundWidth / 2.0 - width / 2.0; //计算字体居中的开始位置

  context.fillText(text, sw, fontsize + 2);
  return { canvas, w: canvas.width, h: canvas.height };
};

function fillRoundRect(
  cxt,
  x,
  y,
  width,
  height,
  radius,
  /*optional*/ fillColor
) {
  //圆的直径必然要小于矩形的宽高
  if (2 * radius > width || 2 * radius > height) {
    return false;
  }

  cxt.save();
  cxt.translate(x, y);
  //绘制圆角矩形的各个边
  drawRoundRectPath(cxt, width, height, radius);
  cxt.fillStyle = fillColor || "#000"; //若是给定了值就用给定的值否则给予默认值
  cxt.fill();
  cxt.restore();
}

function drawRoundRectPath(cxt, width, height, radius) {
  cxt.beginPath(0);
  //从右下角顺时针绘制，弧度从0到1/2PI
  cxt.arc(width - radius, height - radius, radius, 0, Math.PI / 2);

  //矩形下边线

  //绘制提示框那个尖尖角，强吧
  cxt.lineTo(width / 2.0 + 4, height);
  cxt.lineTo(width / 2.0, height + 6);
  cxt.lineTo(width / 2.0 - 4, height);

  cxt.lineTo(radius, height);

  //左下角圆弧，弧度从1/2PI到PI
  cxt.arc(radius, height - radius, radius, Math.PI / 2, Math.PI);

  //矩形左边线
  cxt.lineTo(0, radius);

  //左上角圆弧，弧度从PI到3/2PI
  cxt.arc(radius, radius, radius, Math.PI, (Math.PI * 3) / 2);

  //上边线
  cxt.lineTo(width - radius, 0);

  //右上角圆弧
  cxt.arc(width - radius, radius, radius, (Math.PI * 3) / 2, Math.PI * 2);

  //右边线
  cxt.lineTo(width, height - radius);
  cxt.closePath();
}

function getCanvasWidth(text, fontsize) {
  var canvas = document.createElement("canvas");
  var ctx = canvas.getContext("2d");
  ctx.font = fontsize + "px Arial";
  ctx.textBaseline = "middle";
  var width = ctx.measureText(text).width;
  var boundWidth = width;
  return boundWidth;
}
