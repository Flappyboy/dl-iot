import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import { Grid, Input, Table, Icon } from '@alifd/next';
import { } from '../../../../api';

const { Row, Col } = Grid;

const mockData = {
  users: [
    {
      avatar: require('./images/TB1j159r21TBuNjy0FjXXajyXXa-499-498.png_80x80.jpg'),
      name: 'Susan Day',
      email: 'carol@example.com',
      access: 'vip',
    },
  ],
  purchases: [
    {
      product: 'iPhone X',
      date: 'today',
      state: 'pending',
      price: '$999.99',
    },
    {
      product: 'MacBook Pro',
      date: 'today',
      state: 'pending',
      price: '$2999.00',
    },
  ],
};

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
    this.state = {};
  }

  handleSearch = (value) => {
    console.log(value);
  };

  renderAvatar = (value) => {
    return <img src={value} alt="" style={styles.userAvatar} />;
  };

  renderUserInfo = (value, index, record) => {
    return (
      <div style={styles.userInfo}>
        <h6 style={styles.userName}>{record.name}</h6>
        <p style={styles.userEmail}>{record.email}</p>
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

  renderOper = () => {
    return <Icon type="search" style={styles.editIcon} />;
  };

  renderState = (value) => {
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
              <div style={styles.searchInputCol}>
                <Input
                  style={styles.searchInput}
                  placeholder="Search Users ..."
                  hasClear
                  onChange={this.handleSearch}
                  size="large"
                />
              </div>
              <Table dataSource={mockData.users} hasBorder={false}>
                <Table.Column
                  title="ID"
                  dataIndex="avatar"
                  cell={this.renderAvatar}
                />
                <Table.Column
                  title="GATEWAY"
                  dataIndex="name"
                  cell={this.renderUserInfo}
                />
                <Table.Column
                  title="STATE"
                  dataIndex="access"
                  cell={this.renderAccess}
                />
                <Table.Column title="" cell={this.renderOper} />
              </Table>
            </IceContainer>
          </Col>
          <Col xxx="24" s="12">
            <IceContainer style={{ padding: 0 }}>
              <h2 style={styles.title}>Sensor</h2>
              <div style={styles.searchInputCol}>
                <Input
                  style={styles.searchInput}
                  placeholder="Search Purchases ..."
                  hasClear
                  onChange={this.handleSearch}
                  size="large"
                />
              </div>
              <Table dataSource={mockData.purchases} hasBorder={false}>
                <Table.Column title="ID" dataIndex="product" />
                <Table.Column title="STATE" dataIndex="date" />
                <Table.Column
                  title="STATE"
                  dataIndex="state"
                  cell={this.renderState}
                />
                <Table.Column title="PRICE" dataIndex="price" />
                <Table.Column title="" cell={this.renderOper} />
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
