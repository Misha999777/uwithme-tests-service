import * as React from "react";
import Nav from "react-bootstrap/Nav";
import Button from "react-bootstrap/Button";
import "../styles/AppHeader.css";

export default class AppHeader extends React.Component {

  handleLogout = () => {
    this.props.handleLogout();
  };

  render() {
    return (
      <div className="app-header">
        <Nav className="justify-content-between main-nav navbar">
            <h>Система тестирования</h>
            <Button variant="purple" onClick={this.handleLogout}>Выйти</Button>
        </Nav>
      </div>
    );
  }
}
