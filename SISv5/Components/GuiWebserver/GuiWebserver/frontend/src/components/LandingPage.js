import React, { Component } from 'react';
import Header from './Header';
import {
  HOMEPAGE, INPUTFILEDISPLAY, CLEANDATAPAGE, CONTRACTPAGE, BILLINGPAGE,
} from './PageNames';

class LandingPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      page: HOMEPAGE,
    };
    this.handlePageChange = this.handlePageChange.bind(this);
  }

  handlePageChange(val) {
    if (this.state.page !== val) {
      this.setState({
        page: val,
      });
    }
  }

  render() {
    // const { data, loaded, placeholder } = this.state;
    // if (this.state.page === HOMEPAGE) {
    //   return (
    //     <div>
    //       <Header page={this.state.page} handlePageChange={this.handlePageChange} />
    //       <div className="panel panel-default panel-fixed">
    //         <div className="panel-body">
    //           <DataProvider endpoint="workforce/api/timeEntryFilter/" />
    //           {' '}
    //           {/* //render={data => <Table data={data} />} /> */}
    //         </div>
    //       </div>
    //     </div>
    //   );
    // } if (this.state.page === INPUTFILEDISPLAY) {
    //   return (
    //     <div>
    //       <Header page={this.state.page} handlePageChange={this.handlePageChange} />
    //       <div className="panel panel-default">
    //         <InputFileData />
    //       </div>
    //     </div>
    //   );
    // } if (this.state.page === CLEANDATAPAGE) {
    //   return (
    //     <div>
    //       <Header page={this.state.page} handlePageChange={this.handlePageChange} />
    //       <div className="panel panel-default panel-fixed">
    //         <div className="panel-body">
    //           <CleanDataPage endpoint="workforce/api/cleanTimeEntryFilter/" />
    //           {' '}
    //           {/* //render={data => <Table data={data} />} /> */}
    //         </div>
    //       </div>
    //     </div>
    //   );
    // } if (this.state.page === CONTRACTPAGE) {
    //   return (
    //     <div>
    //       <Header page={this.state.page} handlePageChange={this.handlePageChange} />
    //       <div className="panel panel-default">
    //         <ContractPage />
    //       </div>
    //     </div>
    //   );
    // } if (this.state.page === BILLINGPAGE) {
    //   return (
    //     <div>
    //       <Header page={this.state.page} handlePageChange={this.handlePageChange} />
    //       <div className="panel panel-default panel-fixed">
    //         <BillingPage />
    //       </div>
    //     </div>
    //   );
    // }
    return (
      <div>
        <Header page={this.state.page} handlePageChange={this.handlePageChange} />

      </div>
    );
  }
}

export default LandingPage;
