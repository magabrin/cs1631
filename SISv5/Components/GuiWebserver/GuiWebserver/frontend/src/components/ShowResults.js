import React, { Component } from 'react';
import PropTypes from 'prop-types';
import '../../static/poster.css';

class ShowResults extends Component {
  constructor(props) {
    super(props); 
  }

  getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        let cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
  }

  handleSubmit = (event) => {
    event.preventDefault();

    let myForm = new FormData()
    myForm.append("password", event.target.elements.password.value);
    myForm.append("n", event.target.elements.n.value);

    fetch("/showResults/", {
        method: "POST",
        body: myForm,
        headers: {
            'X-CSRFToken': this.getCookie('csrftoken'),
        },
        credentials: 'include'
    })
    .then(() => {this.props.tallyTableInit() })
    .then(() => {console.log("Show Results fetch complete")})
  }

  render() {
    return (
        <div>
            <h2 className="chris">Show Voting Results</h2>
            <form onSubmit={this.handleSubmit}>
                <label>Admin Password:</label>
                <input type="text" name="password"></input>
                <label>Number of Posters:</label>
                <input type="number" name="n"></input>
                <input type="submit" value="Submit"></input>
            </form>
        </div>
    );
  }
}

export default ShowResults;
