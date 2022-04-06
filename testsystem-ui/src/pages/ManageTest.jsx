import * as React from "react";
import {Button, Form} from "react-bootstrap";
import {BootstrapTable, TableHeaderColumn} from "react-bootstrap-table";

import * as Utils from "../util/APIUtils";

import "../styles/ManageBooks.css";

export default class ManageTest extends React.Component {
    state = {
        questions: [],
        testSessions: [],
        options: Object(),
        options1: Object(),
        selectRowProp: Object(),
        title: "",
        duration: "",
        number: ""
    };

    getMeta = (getTestRequest) => {
        this.props.switchDone(true);
        Utils.getMeta(getTestRequest).then(response => {
            this.props.switchDone(false);
            this.setState({
                title: response.name,
                duration: response.durationMinutes,
                number: response.questionsNumber,
                questions: response.questions,
                testSessions: response.testSessions
            });
        });
    };

    componentDidMount = () => {
        if (!localStorage.getItem("selectedTest")) {
            this.props.history.push("/addTest");
        } else {
            if (this.props.testID != null) {
                this.getMeta({testID: this.props.testID});
            }
            this.setState({
                questions: [],
                selectRowProp: {
                    mode: "checkbox"
                },
                options: {
                    onRowClick: this.onRowClick,
                    afterDeleteRow: this.onAfterDeleteRow,
                    deleteBtn: this.createCustomDeleteButton,
                    insertBtn: this.createCustomInsertButton
                },
                options1: {
                    onRowClick: this.onRowClick1,
                    afterDeleteRow: this.onAfterDeleteRow1,
                    deleteBtn: this.createCustomDeleteButton1
                }
            });
        }
    };

    componentWillReceiveProps = (nextProps) => {
        if (nextProps.testID != null && nextProps.testID !== this.props.testID) {
            this.getMeta({testID: nextProps.testID});
        }
    };

    createCustomDeleteButton = (onClick) => {
        return (
            <button className="delete-books-button" onClick={onClick}>
                <Button variant="red">Удалить вопрос</Button>
            </button>
        );
    };

    createCustomInsertButton = () => {
        return (
            <div className="delete-books-button">
                <Button variant="purple" onClick={() => this.props.history.push("/addQuestion")}>Добавить вопрос</Button>
            </div>
        );
    };

    createCustomDeleteButton1 = (onClick) => {
        return (
            <div className="delete-books-button">
                <Button variant="red" onClick={onClick}>Удалить результат</Button>
            </div>
        );
    };

    onAfterDeleteRow = (rowKeys) => {
        if (this.state.testSessions.length !== 0) {
            alert("Сначала вы должны удалить результаты студентов");
            this.props.switchDone(true);
            this.getMeta({testID: this.props.testID});
        } else {
            this.props.switchDone(true);
            for (let i = 0; i < this.state.questions.length; i++) {
                for (let j = 0; j < rowKeys.length; j++) {
                    if (rowKeys[j] === this.state.questions[i].text) {
                        Utils.deleteQuestion({
                            id: this.state.questions[i].id
                        }).then(() => {
                            this.getMeta({testID: localStorage.getItem("selectedTest")});
                        });
                    }
                }
            }
        }
    };

    onAfterDeleteRow1 = (rowKeys) => {
        this.props.switchDone(true);
        for (let i = 0; i < this.state.testSessions.length; i++) {
            for (let j = 0; j < rowKeys.length; j++) {
                if (rowKeys[j] === this.state.testSessions[i].userName) {
                    Utils.deleteResult({
                        id: this.state.testSessions[i].id,
                        testID: this.props.testID
                    }).then(() => {
                        this.getMeta({testID: localStorage.getItem("selectedTest")});
                    });
                }
            }
        }
    };

    handleChange = (event) => {
        const target = event.target;
        this.setState(current => ({...current, [target.id]: target.value}));
    };

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.switchDone(true);
        Utils.updateTest({
            id: this.props.testID,
            name: this.state.title,
            durationMinutes: this.state.duration,
            questionsNumber: this.state.number
        }).then(() => {
            this.props.switchDone(false);
            this.props.updateLeftMenu();
        });
    };

    onRowClick = (row) => {
        this.props.openQuestion(row.id);
    };

    onRowClick1 = (row) => {
        this.props.openResults(row.id);
    };

    onDeleteTest() {
        this.props.switchDone(true);
        Utils.deleteTest({
            id: this.props.testID
        }).then(() => {
            localStorage.removeItem("selectedTest");
            this.props.switchDone(false);
            this.props.updateLeftMenu();
        });
    }

    render() {
        return (
            <div className="manage-books-table">
                <Form onSubmit={this.handleSubmit} autoComplete="off" noValidate>
                    <Form.Label className="text-center book-label text"  column={true}>
                        Настройки теста
                    </Form.Label>

                    <Form.Group controlId="title">
                        <Form.Label column={false}>Название</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.title}
                            onChange={this.handleChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="number">
                        <Form.Label column={false}>Количество вопросов</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.number}
                            onChange={this.handleChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="duration">
                        <Form.Label column={false}>Продолжительность теста</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.duration}
                            onChange={this.handleChange}
                        />
                    </Form.Group>

                    <Button variant="purple" type="submit">
                        Изменить
                    </Button>
                    <Button
                        id={"delete-test"}
                        variant="red"
                        onClick={this.onDeleteTest.bind(this)}
                    >
                        Удалить тест
                    </Button>
                </Form>

                <hr/>
                <BootstrapTable
                    ref="table"
                    data={this.state.questions}
                    selectRow={this.state.selectRowProp}
                    deleteRow
                    insertRow
                    options={this.state.options}
                >
                    <TableHeaderColumn className="text" headerAlign="center" dataField="text" isKey={true}>
                        Вопросы теста
                    </TableHeaderColumn>
                </BootstrapTable>

                <BootstrapTable
                    ref="table"
                    data={this.state.testSessions}
                    selectRow={this.state.selectRowProp}
                    deleteRow={true}
                    options={this.state.options1}
                >
                    <TableHeaderColumn headerAlign="center"
                                       className="text"
                                       dataField="userName"
                                       isKey={true}>
                        Студент
                    </TableHeaderColumn>
                    <TableHeaderColumn
                        headerAlign="center"
                        className="text"
                        dataField="score"
                        isKey={false}
                    >
                        Результат
                    </TableHeaderColumn>
                    <TableHeaderColumn
                        headerAlign="center"
                        className="text"
                        dataField="elapsedTime"
                        isKey={false}
                    >
                        Время
                    </TableHeaderColumn>
                </BootstrapTable>
            </div>
        );
    }
}
