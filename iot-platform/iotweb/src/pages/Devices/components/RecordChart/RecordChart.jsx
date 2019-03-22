import React, { Component } from 'react';
import echarts from 'echarts';

const graphStyle = {
  height: 700,
};
let myChart;
function randomData() {
  now = new Date(+now + oneDay);
  value = value + Math.random() * 21 - 10;
  return {
    name: now.toString(),
    value: [
      [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
      Math.round(value),
    ],
  };
}

const data = [];
let now = +new Date(1997, 9, 3);
let oneDay = 24 * 3600 * 1000;
let value = Math.random() * 1000;
for (let i = 0; i < 1000; i++) {
  data.push(randomData());
}
export default class RecordChart extends Component {
  static displayName = 'RecordChart';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {

    };
  }

  componentDidUpdate(prevProps) {
    // if (this.props.isLoading) {
    //   myChart.showLoading();
    // } else {
    //   myChart.hideLoading();
    //   this.loadData(this.props.data);
    // }
  }

  componentDidMount() {
    myChart = echarts.init(document.getElementById('graph'));
    myChart.showLoading();
    this.loadData();
  }

  loadData = () => {
    // 基于准备好的dom，初始化echarts实例
    // 绘制图表


    myChart.hideLoading();

    const option = {
      title: {
        text: '动态数据 + 时间坐标轴'
      },
      tooltip: {
        trigger: 'axis',
        formatter: (params) => {
          params = params[0];
          const date = new Date(params.name);
          return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        axisPointer: {
          animation: false
        }
      },
      xAxis: {
        type: 'time',
        splitLine: {
          show: false
        }
      },
      yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
          show: false
        }
      },
      series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data,
      }],
    };
    myChart.setOption(option);

    setInterval(() => {
      for (let i = 0; i < 5; i++) {
        data.shift();
        data.push(randomData());
      }

      myChart.setOption({
        series: [{
          data,
        }],
      });
    }, 1000);
  }

  render() {
    return (
      <div id="graph" style={graphStyle} />
    );
  }
}
