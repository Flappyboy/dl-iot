import React, { Component } from 'react';
import echarts from 'echarts';

const recordSort = (r1, r2) => {
  if (r1.timestamp > r2.timestamp) {
    return 1;
  } else if (r1.timestamp < r2.timestamp) {
    return -1;
  }
  return 0;
};
const graphStyle = {
  height: 400,
};

export default class RecordChart extends Component {
  static displayName = 'RecordChart';

  static propTypes = {};

  static defaultProps = {};

  myChart = null;
  data = [];
  series = [];

  selectedStart = null;
  selectedEnd = null;

  constructor(props) {
    super(props);
    this.selectTime = props.selectTime;
    this.state = {
      sensor: props.sensor,
      record: props.record,
      disableRealTime: props.disableRealTime,
    };
  }

  componentWillReceiveProps(nextProps) {
    this.state.sensor = nextProps.sensor;
    this.state.record = nextProps.record;
    this.state.disableRealTime = nextProps.disableRealTime;
    this.setState({});
    this.componentDidMount();
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
    if (this.myChart != null) {
      this.myChart.dispose();
    }
    this.series = [];
    this.myChart = echarts.init(document.getElementById(this.state.record.mean));
    this.myChart.showLoading();

    const records = this.state.record.records;
    records.sort(recordSort);
    // console.log(records);
    let pre = -10000000;
    records.forEach((record) => {
      if (record.timestamp - pre > 10 * 1000) {
        this.data = [];
        this.series.push({
          smooth: false,
          type: 'line',
          stack: 'a',
          lineStyle: {
            width: 1,
          },
          color: 'black',
          symbolSize: 0,
          data: this.data,
        });
      }
      const time = new Date(record.timestamp);
      pre = record.timestamp;
      this.data.push([time, record.value]);
    });
    console.log(this.data);
    this.loadData();
  }

  componentWillUnmount() {
    if (this.myChart != null) {
      this.myChart.dispose();
    }
  }

  loadData = () => {
    
    

    // this.myChart.dispatchAction({
    //   type: 'brush',
    //   areas: [
    //     {
    //       brushType: 'lineX',
    //     },
    //   ],
    // });

    const option = {
      title: {
        left: 'center',
        text: this.state.record.mean,
      },
      toolbox: {
        show: true,
        right: '10%',
        feature: {
          myTool1: {
            show: true,
            title: 'Query selected area',
            icon: 'path://M432.45,595.444c0,2.177-4.661,6.82-11.305,6.82c-6.475,0-11.306-4.567-11.306-6.82s4.852-6.812,11.306-6.812C427.841,588.632,432.452,593.191,432.45,595.444L432.45,595.444z M421.155,589.876c-3.009,0-5.448,2.495-5.448,5.572s2.439,5.572,5.448,5.572c3.01,0,5.449-2.495,5.449-5.572C426.604,592.371,424.165,589.876,421.155,589.876L421.155,589.876z M421.146,591.891c-1.916,0-3.47,1.589-3.47,3.549c0,1.959,1.554,3.548,3.47,3.548s3.469-1.589,3.469-3.548C424.614,593.479,423.062,591.891,421.146,591.891L421.146,591.891zM421.146,591.891',
            option: {},
            onclick: () => {
              if (this.selectedStart != null || this.selectedEnd != null) {
                this.selectTime(this.selectedStart, this.selectedEnd);
              }
            },
          },
        },
      },
      brush: {
        toolbox: ['lineX', 'clear'],
        xAxisIndex: 'all',
        throttleType: 'debounce',
        throttleDelay: 300,
      },
      tooltip: {
        trigger: 'axis',
        formatter: (params) => {
          params = params[0];
          return `${params.value[0]}:${params.value[1]}`;
        },
        axisPointer: {
          animation: false,
        },
      },
      xAxis: {
        type: 'time',
        splitLine: {
          show: false,
        },
      },
      yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
          show: false,
        },
      },
      series: this.series,
    };
    this.myChart.setOption(option);

    this.myChart.on('brushSelected', (params) => {
      console.log(params);
      if (!params.batch[0].areas.length === 0) {
        this.selectedStart = null;
        this.selectedEnd = null;
        return;
      }
      if (params.batch[0].areas[0] === undefined) {
        return;
      }
      const range = params.batch[0].areas[0].coordRange;
      if (range.length < 2) {
        this.selectedStart = null;
        this.selectedEnd = null;
      } else {
        this.selectedStart = Math.round(range[0]) - 1;
        this.selectedEnd = Math.round(range[1]) + 1;
      }
    });

    this.myChart.on('mouseup', (params) => {
      console.log(params);
    });
    this.myChart.hideLoading();
    // setInterval(() => {
    //   for (let i = 0; i < 5; i++) {
    //     data.shift();
    //     data.push(randomData());
    //   }

    //   myChart.setOption({
    //     series: [{
    //       data,
    //     }],
    //   });
    // }, 1000);
  }

  render() {
    return (
      <div id={this.state.record.mean} style={graphStyle} />
    );
  }
}
