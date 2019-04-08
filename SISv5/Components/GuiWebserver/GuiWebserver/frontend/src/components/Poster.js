import React, { Component } from 'react';
import PropTypes from 'prop-types';
import '../../static/poster.css';
import ShowResults from './ShowResults';
import KillVoting from './KillVoting';

class Poster extends Component {
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
    myForm.append("voterEmail", event.target.elements.voterEmail.value);
    myForm.append("posterNumber", event.target.elements.posterNumber.value);
    console.log(event.target.elements.voterEmail);
    console.log(event.target.elements.voterEmail.value);
    console.log(myForm);
    fetch("/submitPoster/", {
        method: "POST",
        body: myForm,
        headers: {
            'X-CSRFToken': this.getCookie('csrftoken'),
        },
        credentials: 'include'
    })
    .then(() => {console.log("Poster fetch complete")})
    .then(() => {console.log(this.getCookie('csrftoken'))})
  }

  render() {
    return (
        <div>
            <h2 className="chris">Vote for a Poster</h2>
            <form onSubmit={this.handleSubmit}>
                <label>Voter Email:</label>
                <input type="email" name="voterEmail"></input>
                <label>Poster Number:</label>
                <input type="number" name="posterNumber"></input>
                <input type="submit" value="Submit"></input>
            </form>
            <ShowResults />
            <KillVoting />
        </div>
    );
  }
}

export default Poster;
