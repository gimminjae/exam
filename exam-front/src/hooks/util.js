const util = {
  makeSearchParam(param) {
    let searchParams = []
    if (!(param === null || param === undefined)) {
      for (let key in param) {
        searchParams.push(`${key}=${param[key]}`)
      }
    }
    return `${searchParams.length === 0 ? '' : '?'}${searchParams.join('&')}`
  },
  showBasicError(error) {
    return error.response.data || '오류가 발생하였습니다.'
  }
}

export default util
