import React, { Component } from 'react';
import PropTypes from 'prop-types';
import '../../static/poster.css';

class Poster extends Component {
  constructor(props) {
    super(props); 
  }

  getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
  }

  handleSubmit(event) {
    let myForm = new FormData()
    myForm.append("test", "chris");

    event.preventDefault();
    fetch("/submitPoster", {
        method: "POST",
        body: myForm,
        headers: {
            'X-CSRFToken': this.getCookie('csrftoken'),
        }
    })
  }

  render() {
    return (
        <div>
            <h2 className="chris">Poster hey</h2>
            <form onSubmit={this.handleSubmit}>
                <input type="text"></input>
                <input type="submit" value="Submit"></input>
            </form>
        </div>
    );
  }
}

export default Poster;
