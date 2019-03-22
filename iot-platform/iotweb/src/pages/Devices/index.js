import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import ColumnsTable from './components/ColumnsTable';
import RecordChart from './components/RecordChart';


export default class Devices extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <IceContainer className="devices-page">
        <ColumnsTable />
        <RecordChart />
      </IceContainer>
    );
  }
}
