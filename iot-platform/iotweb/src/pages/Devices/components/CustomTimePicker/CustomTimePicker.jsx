import React, { Component } from 'react';
import { Switch, DatePicker, TimePicker } from '@alifd/next';
import moment from 'moment';

export default class CustomTimePicker extends React.Component {
  constructor(props, context) {
    super(props, context);
    this.change = (start, end) => {
      if (props.onChange) {
        if (start == null) {
          start = -1;
        } else {
          start = start.valueOf();
        }
        if (end == null) {
          end = -1;
        } else {
          end = end.valueOf();
        }
        props.onChange(start, end);
      }
    };
    this.state = {
      startValue: null,
      endValue: null,
      endOpen: false,
    };
  }

  disabledStartDate = (startValue) => {
    const { endValue } = this.state;
    if (!startValue || !endValue) {
      return false;
    }
    return startValue.valueOf() > endValue.valueOf();
  }

  disabledEndDate = (endValue) => {
    const { startValue } = this.state;
    if (!endValue || !startValue) {
      return false;
    }
    return endValue.valueOf() <= startValue.valueOf();
  }

  onChange = (stateName, value) => {
    this.state[stateName] = value;
    this.setState({
      [stateName]: value,
    });
    this.change(this.state.startValue, this.state.endValue);
  }

  onStartChange = (value) => {
    this.onChange('startValue', value);
  }

  onEndChange = (value) => {
    this.onChange('endValue', value);
  }

  handleStartOpenChange = (open) => {
    if (!open) {
      this.setState({ endOpen: true });
    }
  }

  handleEndOpenChange = (open) => {
    this.setState({ endOpen: open });
  }

  render() {
    const { endOpen } = this.state;
    return (
      <div>
        <DatePicker
          disabledDate={this.disabledStartDate}
          placeholder="Departure Date"
          onChange={this.onStartChange}
          onVisibleChange={this.handleStartOpenChange}
        />
        <span className="custom-sep">-</span>
        <DatePicker
          disabledDate={this.disabledEndDate}
          placeholder="Return Date"
          onChange={this.onEndChange}
          visible={endOpen}
          onVisibleChange={this.handleEndOpenChange}
        />
      </div>
    );
  }
}
