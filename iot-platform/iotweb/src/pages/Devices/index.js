import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import ColumnsTable from './components/ColumnsTable';
import Record from './components/Record';


export default class Devices extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sensor: null,
    };
  }

  selectSensor = (sensor) => {
    // console.log(sensor);
    this.setState({
      sensor,
    });
  }

  renderRecord() {
    if (this.state.sensor == null) {
      return;
    }
    return (
      <Record sensor={this.state.sensor} />
    );
  }

  render() {
    return (
      <IceContainer className="devices-page">
        <ColumnsTable selectSensor={this.selectSensor} />
        {this.renderRecord()}
      </IceContainer>
    );
  }
}
