import React, { Component } from 'react';
import echarts from 'echarts';
import { queryRecordForSensor } from '../../../../api';
import { min } from 'moment';

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


  interval = null;

  lastTime = -1000000;

  selectedStart = null;
  selectedEnd = null;

  constructor(props) {
    super(props);
    this.selectTime = props.selectTime;
    this.state = {
      sensor: props.sensor,
      record: props.record,
      realTime: props.realTime,
    };
  }

  shouldComponentUpdate(newProps, newState) {
    return true;
  }

  componentWillReceiveProps(nextProps) {
    if (!this.state.realTime && nextProps.realTime) {
      console.log('startRealTime');
      this.startRealTime();
    } else if (this.state.realTime && !nextProps.realTime) {
      console.log('stopRealTime');
      clearInterval(this.interval);
      this.interval = null;
    }

    this.state.sensor = nextProps.sensor;
    this.state.record = nextProps.record;
    this.state.realTime = nextProps.realTime;
    this.setState({});
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevProps == null || prevProps.record === this.state.record) {
      return;
    }
    this.componentDidMount();
  }

  componentDidMount() {
    if (this.myChart != null) {
      this.myChart.dispose();
    }
    this.series = [];
    this.lastTime = -1000000;
    if (this.interval != null) {
      clearInterval(this.interval);
      this.interval = null;
    }
    this.selectedStart = null;
    this.selectedEnd = null;

    this.myChart = echarts.init(document.getElementById(this.state.record.mean));
    this.myChart.showLoading();

    const records = this.state.record.records;
    this.update(records);
    // console.log(this.data);
    this.loadData();
  }

  componentWillUnmount() {
    if (this.myChart != null) {
      this.myChart.dispose();
    }
    if (this.interval != null) {
      clearInterval(this.interval);
      this.interval = null;
    }
  }

  queringFlag = false;
  startRealTime = () => {
    this.state.realTime = true;
    this.setState({});
    console.log('real time');
    this.interval = setInterval(this.updateRealTime.bind(this), 1000);
  }

  updateRealTime = () => {
    if (this.queringFlag) {
      return;
    }
    this.queringFlag = true;
    const time = (new Date()).getTime() - 10 * 60 * 1000;
    const params = {
      id: this.state.sensor.id,
      startTime: Math.max(this.lastTime, time),
      endTime: -1,
      mean: this.state.record.mean,
    };

    queryRecordForSensor(params).then((response) => {
      console.log(`realtime ${response}`);
      if (response.data.length === 0 || response.data[0].records.length === 0) {
        this.queringFlag = false;
        return;
      }
      const num = this.update(response.data[0].records);
      if (num > 0) {
        this.delRecords(time, response.data[0].records.length);
      }
      this.myChart.setOption({
        series: this.series,
      });

      this.queringFlag = false;
    }).catch((error) => {
      console.log(error);
      this.queringFlag = false;
    });
  }
  delRecords = (time) => {
    while (this.series.length > 0) {
      const data = this.series[0].data;
      if (data.length === 0) {
        this.series.shift();
        continue;
      }
      const r = data[0];
      if (r[2] >= time) {
        break;
      }
      data.shift();
    }
  }


  update = (records) => {
    let num = 0;
    records.sort(recordSort);
    // console.log(records);
    if (records.length <= 0) {
      return num;
    }
    const start = records[0].timestamp;
    const end = records[records.length - 1].timestamp;

    const interval = Math.max(10 * 1000, (start - end) * 2 / records.length);
    records.forEach((record) => {
      const dif = record.timestamp - this.lastTime;
      if (dif <= 0) {
        return;
      }
      if (dif > interval) {
        this.data = [];
        this.series.push({
          smooth: false,
          type: 'line',
          stack: 'a',
          lineStyle: {
            width: 1,
          },
          color: 'black',
          animation: false,
          showSymbol: false,
          hoverAnimation: true,
          data: this.data,
        });
      }
      const time = new Date(record.timestamp);
      this.lastTime = record.timestamp;
      num += 1;
      this.data.push([time, record.value, record.timestamp]);
    });
    return num;
  }

  loadData = () => {
    console.log(this.series);
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
      // console.log(params);
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
    this.myChart.hideLoading();
  }

  render() {
    return (
      <div id={this.state.record.mean} style={graphStyle} />
    );
  }
}
