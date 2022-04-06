import * as React from "react";
import { Pagination } from "react-bootstrap";
import "../styles/Paging.css";

export default class ResultsPaging extends React.Component {
  state = {
    selectedQuestion: 0
  };

  handleMenuClick = (id) => {
    this.props.selectQuestion(id);
    this.setState({ selectedQuestion: id });
    this.forceUpdate();
  };

  getClass = (id) => {
    let questionId = this.props.questions[id].id;
    let correctAnswers = this.props.questions[id].answers.filter((answer) => answer.correct).map((answer) => answer.text);
    let userAnswers = this.props.userAnswersByQuestionId[questionId];
    let checker = (arr, target) => target.every(v => arr.includes(v));
    if (id === this.state.selectedQuestion) {
      return "selected";
    } else if (checker(correctAnswers, userAnswers)) {
      return "answered";
    } else {
      return "wrong";
    }
  };

  render() {
    if (this.props.data === null) {
      return null;
    }
    const listItems = this.props.questions.map((item) => (
      <Pagination.Item
        key={item.name}
        action
        onClick={() => this.handleMenuClick(this.props.questions.indexOf(item))}
      >
        <div className={this.getClass(this.props.questions.indexOf(item))}>
          {this.props.questions.indexOf(item) + 1}
        </div>
      </Pagination.Item>
    ));

    return (
      <div className="Paging">
        <Pagination>{listItems}</Pagination>
      </div>
    );
  }
}
