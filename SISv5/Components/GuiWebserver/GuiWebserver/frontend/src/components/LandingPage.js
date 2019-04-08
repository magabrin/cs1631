import React, { Component } from 'react';
import Header from './Header';
import Poster from './Poster'
import {
  HOMEPAGE, INPUTFILEDISPLAY, CLEANDATAPAGE, CONTRACTPAGE, BILLINGPAGE,
} from './PageNames';
import InitTally from './InitTally';

class LandingPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tallyTableInit: false,
    };
  }

  initTallyTable = () => {
    console.log("changin tallyTableInit to true");
    this.setState({
      tallyTableInit: true,
    });
  }

  render() {
  
    return (
      <div>
        <Header page={this.state.page} handlePageChange={this.handlePageChange} />
        {this.state.tallyTableInit ? <Poster /> : <InitTally tallyTableInit={this.initTallyTable}/>}
      </div>
    );
  }
}

export default LandingPage;


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