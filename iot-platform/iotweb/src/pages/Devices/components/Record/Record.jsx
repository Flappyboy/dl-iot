import React, { Component } from 'react';
import { Switch, DatePicker, TimePicker, Button, Grid } from '@alifd/next';
import moment from 'moment';
import RecordChart from '../RecordChart';
import { queryRecordForSensor } from '../../../../api';
import CustomTimePicker from '../CustomTimePicker';

const { Row, Col } = Grid;

export default class Record extends Component {
  static displayName = 'Record';

  static propTypes = {};

  static defaultProps = {};

  readyForRealTime = false;

  constructor(props) {
    super(props);
    this.state = {
      sensor: props.sensor,
      realTime: false,
      disableSwitch: false,
      records: [],
      startDate: -1,
      endDate: -1,
    };
  }

  componentWillReceiveProps(nextProps) {
    this.state.sensor = nextProps.sensor;
    this.state.realTime = false;
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
    const params = {
      id: this.state.sensor.id,
      startTime: -1,
      endTime: -1,
      last: 10 * 60 * 1000,
    };
    queryRecordForSensor(params).then((response) => {
      this.setState({
        records: response.data,
      });
      this.readyForRealTime = true;
    }).catch((error) => {
      console.log(error);
    });
  }

  onRealTimeChange = (checked) => {
    console.log(`switch to ${checked}`);
    // if (this.readyForRealTime) {
    // this.state.realTime = checked;
    // this.setState({
    //   realTime: checked,
    // });
    // } else {
    //   this.componentDidMount();
    // }
  }

  onRealTimeClick = () => {
    this.setState({
      realTime: !this.state.realTime,
    });
  }

  onDateChange = (start, end) => {
    this.setState({
      startDate: start,
      endDate: end,
    });
    // console.log(start);
    // console.log(end);
  }

  queryFromDate = () => {
    const params = {
      id: this.state.sensor.id,
      startTime: this.state.startDate,
      endTime: this.state.endDate,
      limit: 700,
    };
    queryRecordForSensor(params).then((response) => {
      this.readyForRealTime = false;
      this.setState({
        records: response.data,
        realTime: false,
      });
    }).catch((error) => {
      console.log(error);
    });
  }

  queryFromTime = (start, end) => {
    const params = {
      id: this.state.sensor.id,
      startTime: start,
      endTime: end,
      limit: 700,
    };
    queryRecordForSensor(params).then((response) => {
      this.readyForRealTime = false;
      this.setState({
        records: response.data,
        realTime: false,
      });
    }).catch((error) => {
      console.log(error);
    });
  }

  renderChart = () => {
    return (
      <div>
        {this.state.records.map((record) => (
          <RecordChart sensor={this.state.sensor} record={record} realTime={this.state.realTime} selectTime={this.queryFromTime.bind(this)} />
        ))}
      </div>
    );
  }

  render() {
    return (
      <div>
        <Row >
          <Col m={2} s={1} xs={0} />
          <Col map={5} s={7} xs={12}>
            <CustomTimePicker onChange={this.onDateChange} />
          </Col>
          <Col moment={1} s={1} xs={4}>
            <Button type="primary" onClick={this.queryFromDate} >查询</Button>
          </Col>
          <Col m={12} s={1} xs={0} />
          <Col m={2} s={4} xs={8}>
            <Switch style={large} checkedChildren="Real Time" disabled={this.state.disableSwitch} checked={this.state.realTime} onClick={this.onRealTimeClick} onChange={this.onRealTimeChange} unCheckedChildren="History" />
          </Col>
        </Row>
        {this.renderChart()}
      </div>
    );
  }
}

const large = {
  width: 100,
};
