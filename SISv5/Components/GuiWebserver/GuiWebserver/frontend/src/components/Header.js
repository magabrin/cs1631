import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {
  INPUTFILEDISPLAY, CLEANDATAPAGE, CONTRACTPAGE, BILLINGPAGE, HOMEPAGE,
} from './PageNames';

class Header extends Component {
  constructor(props) {
    super(props);
    this.state = {
      page: props.page,
    };
  }

  render() {
    return (

        <h2>Matt And Chris's GUI</h2>
    //   <nav id="nav-global" className="navbar navbar-default navbar-fixed-top navbar-offset">
    //     <div className="navbar-header">
    //       <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
    //         <span className="sr-only">Here's a button</span>
    //         <span className="icon-bar" />
    //       </button>
    //       <h4>Matt And Chris's GUI</h4>
    //       {/* <a className="navbar-brand" id="nav_home" onClick={ () => this.props.handlePageChange("home") }>
    //                     Home
    //                 </a>
    //                 <a className="navbar-brand" id="nav_inputfiledisplay" style={{cursor:"pointer"}} onClick={ () => this.props.handlePageChange("inputfiledisplay") }>
    //                     Input File Display
    //                 </a> */}
    //     </div>
    //     <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    //       <ul className="nav navbar-nav navbar-right">
    //         <li className="navbar-brand icon-inbox" id={HOMEPAGE} style={{ cursor: 'pointer' }} onClick={() => this.props.handlePageChange(HOMEPAGE)}> Raw Data</li>
    //         <li className="navbar-brand icon-inbox" id={INPUTFILEDISPLAY} style={{ cursor: 'pointer' }} onClick={() => this.props.handlePageChange(INPUTFILEDISPLAY)}>Input File Display</li>
    //         <li className="navbar-brand icon-inbox" id={CLEANDATAPAGE} style={{ cursor: 'pointer' }} onClick={() => this.props.handlePageChange(CLEANDATAPAGE)}> Cleaned Data</li>
    //         <li className="navbar-brand icon-inbox" id={CONTRACTPAGE} style={{ cursor: 'pointer' }} onClick={() => this.props.handlePageChange(CONTRACTPAGE)}>Contracts </li>
    //         <li className="navbar-brand icon-inbox" id={BILLINGPAGE} style={{ cursor: 'pointer' }} onClick={() => this.props.handlePageChange(BILLINGPAGE)}>Billing Page </li>
    //       </ul>
    //     </div>
    //   </nav>
    );
  }
}

export default Header;
