'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
      return queryInterface.renameTable("results","result");
  },

  down: (queryInterface, Sequelize) => {
      return queryInterface.renameTable("result","results");
  }
};
