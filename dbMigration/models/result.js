'use strict';
module.exports = (sequelize, DataTypes) => {
  const result = sequelize.define('result', {
    createTime: DataTypes.DATE,
    cat: DataTypes.FLOAT,
    dog: DataTypes.FLOAT
  }, {});
  result.associate = function(models) {
    // associations can be defined here
  };
  return result;
};