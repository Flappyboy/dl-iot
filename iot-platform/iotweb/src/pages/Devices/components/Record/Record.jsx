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

  constructor(props) {
    super(props);
    this.state = {
      sensor: props.sensor,
      disableRealTime: true,
      records: [],
      startDate: -1,
      endDate: -1,
    };
  }

  componentWillReceiveProps(nextProps) {
    this.state.sensor = nextProps.sensor;
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
    }).catch((error) => {
      console.log(error);
    });
  }

  onRealTimeChange = (checked) => {
    // console.log(`switch to ${checked}`);
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
    };
    queryRecordForSensor(params).then((response) => {
      this.setState({
        records: response.data,
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
    };
    queryRecordForSensor(params).then((response) => {
      this.setState({
        records: response.data,
      });
    }).catch((error) => {
      console.log(error);
    });
  }

  renderChart = () => {
    return (
      <div>
        {this.state.records.map((record) => (
          <RecordChart sensor={this.state.sensor} record={record} disableRealTime={this.state.disableRealTime} selectTime={this.queryFromTime.bind(this)} />
        ))}
      </div>
    );
  }

  render() {
    return (
      <div>
        <Row >
          <Col m={2} s={1} xs={0} />
          <Col map={5} s={8} xs={12}>
            <CustomTimePicker onChange={this.onDateChange} />
          </Col>
          <Col moment={1} s={1} xs={4}>
            <Button type="primary" onClick={this.queryFromDate} >查询</Button>
          </Col>
          <Col m={12} s={4} xs={0} />
          <Col m={2} s={6} xs={8}>
            <Switch style={large} checkedChildren="Real Time" disabled={this.state.disableRealTime} onChange={this.onRealTimeChange} unCheckedChildren="History" />
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
