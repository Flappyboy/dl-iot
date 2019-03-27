import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import { Grid, Input, Table, Icon } from '@alifd/next';
import { queryDeviceList, queryRecordForSensor } from '../../../../api';

const { Row, Col } = Grid;

const accessColors = {
  vip: '#343A40',
  pro: '#ABABAB',
  free: '#83B451',
};

const stateColors = {
  pending: '#fdcb6e',
  completed: '#ff7675',
  cancelled: '#2ecc71',
};

export default class ColumnsTable extends Component {
  static displayName = 'ColumnsTable';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.selectSensor = props.selectSensor;
    this.state = {
      deviceList: [],
      deviceLoading: true,
      sensorList: [],
    };
  }

  componentDidMount() {
    // 声明一个自定义事件
    // 在组件装载完成以后
    // this.eventEmitter = emitter.addListener('query_statistics', this.queryStatistics);

    this.updateDevice();
  }

  updateDevice = () => {
    this.setState({
      deviceLoading: true,
    });
    queryDeviceList().then((response) => {
      this.setState({
        deviceList: response.data,
        deviceLoading: false,
      });
    })
      .catch((error) => {
        console.log(error);
      });
  }

  selectDevice = (device) => {
    // console.log(device);
    this.setState({
      sensorList: device.sensors,
    });
  }

  // selectSensor = (sensor) => {
  //   console.log(sensor);
  // }

  handleSearch = (value) => {
    // console.log(value);
  };

  renderId = (value) => {
    return (
      <span style={{ ...styles.userAvatar }}>
        {value}
      </span>
    );
  };

  renderType = (value) => {
    return (
      <span style={{ ...styles.userName }}>
        {value}
      </span>
    );
  };

  renderState = (value, index, device) => {
    if (value === 'ONLINE') {
      return (
        <span style={{ ...styles.userName }}>
          {value}
        </span>
      );
    }
    return (
      <div style={styles.userInfo}>
        <h6 style={styles.userName}>{value}</h6>
        <p style={styles.userEmail}>{device.lastModifiedDate}</p>
      </div>
    );
  };

  renderAccess = (value) => {
    return (
      <span style={{ ...styles.userAccess, background: accessColors[value] }}>
        {value}
      </span>
    );
  };

  renderSelectDevice = (value, index, device) => {
    return (
      <span onClick={this.selectDevice.bind(this, device)}>
        <Icon type="search" style={styles.editIcon} />
      </span>
    );
  };

  renderSelectSensor = (value, index, sensor) => {
    return (
      <span onClick={this.selectSensor.bind(this, sensor)}>
        <Icon type="search" style={styles.editIcon} />
      </span>
    );
  };

  renderStateA = (value) => {
    return (
      <span style={{ ...styles.purchasesState, color: stateColors[value] }}>
        {value}
      </span>
    );
  };
  render() {
    return (
      <div style={styles.container}>
        <Row wrap gutter="20">
          <Col xxx="24" s="12">
            <IceContainer style={{ padding: 0 }}>
              <h2 style={styles.title}>Device</h2>
              {/* <div style={styles.searchInputCol}>
                <Input
                  style={styles.searchInput}
                  placeholder="Search Users ..."
                  hasClear
                  onChange={this.handleSearch}
                  size="large"
                />
              </div> */}
              <Table dataSource={this.state.deviceList} hasBorder={false} loading={this.state.deviceLoading} >
                <Table.Column
                  title="ID"
                  dataIndex="id"
                  cell={this.renderId}
                />
                <Table.Column
                  title="TYPE"
                  dataIndex="type"
                  cell={this.renderType}
                />
                <Table.Column
                  title="STATE"
                  dataIndex="state"
                  cell={this.renderState}
                />
                <Table.Column title="" cell={this.renderSelectDevice} />
              </Table>
            </IceContainer>
          </Col>
          <Col xxx="24" s="12">
            <IceContainer style={{ padding: 0 }}>
              <h2 style={styles.title}>Sensor</h2>
              {/* <div style={styles.searchInputCol}>
                <Input
                  style={styles.searchInput}
                  placeholder="Search Purchases ..."
                  hasClear
                  onChange={this.handleSearch}
                  size="large"
                />
              </div> */}
              <Table dataSource={this.state.sensorList} hasBorder={false} >
                <Table.Column
                  title="ID"
                  dataIndex="id"
                  cell={this.renderId}
                />
                <Table.Column
                  title="TYPE"
                  dataIndex="type"
                  cell={this.renderType}
                />
                <Table.Column
                  title="STATE"
                  dataIndex="state"
                  cell={this.renderState}
                />
                <Table.Column title="" cell={this.renderSelectSensor} />
              </Table>
            </IceContainer>
          </Col>
        </Row>
      </div>
    );
  }
}

const styles = {
  title: {
    padding: '10px 20px',
    margin: '0',
  },
  userAvatar: {
    width: '40px',
    height: '40px',
    borderRadius: '50px',
  },
  userName: {
    margin: '4px 0 3px',
  },
  userEmail: {
    margin: 0,
  },
  userAccess: {
    padding: '3px 6px',
    borderRadius: '5px',
    color: '#fff',
    textTransform: 'capitalize',
  },
  editIcon: {
    color: '#2874D1',
    cursor: 'pointer',
  },
  searchInputCol: {
    padding: '10px 20px',
    background: '#E6EBF4',
  },
  searchInput: {
    width: '100%',
    background: '#F4F6FA',
    border: 'none',
  },
  purchasesState: {
    textTransform: 'capitalize',
  },
};
